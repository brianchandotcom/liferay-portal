/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */
import DOMPurify from 'dompurify';
import {useEffect, useState} from 'react';
import i18n from '../../../../../../common/I18n';
import {Table} from '../../../../../../common/components';
import {fetchHeadless} from '../../../../../../common/services/liferay/api';
import {useCustomerPortal} from '../../../../context';
import ActivationKeysLayout from '../../../../layouts/ActivationKeysLayout';

const Commerce = () => {
	const [
		ActivationInstructionsData,
		setActivationInstructionsData,
	] = useState([]);
	const [
		isLoadingActivationInstructions,
		setIsLoadingActivationInstructions,
	] = useState(false);

	const [{project, sessionId}] = useCustomerPortal();

	const fetchCommerceActivationsKeysInstructions = async () => {
		const webContentFolderName = 'commerce-activation';
		const webContentTemplateName = 'COMMERCE-ACTIVATION-TEMPLATE';

		const siteGroupId = Liferay.ThemeDisplay.getSiteGroupId();

		const structuredContentFolders = await fetchHeadless({
			url: `/sites/${siteGroupId}/structured-content-folders`,
		});

		const {id: commerceActivationInstructionsFolderID} =
			structuredContentFolders.items.find(
				({name}) => name === webContentFolderName
			) || {};

		const contentTemplates = await fetchHeadless({
			url: `/sites/${siteGroupId}/content-templates`,
		});

		const contentTemplate = contentTemplates.items.find(
			({id}) => id === webContentTemplateName
		);

		const structuredContents = await fetchHeadless({
			url: `/structured-content-folders/${commerceActivationInstructionsFolderID}/structured-contents`,
		});

		const renderedInstructionsData = await structuredContents.items.reduce(
			async (structuredContentList, structuredContent) => {
				const promiseStructuredContentList = await structuredContentList;

				const dxpVersion =
					structuredContent.contentFields.find(
						({name}) => name === 'DXPVersion'
					) || {};
				const structuredComponent = await fetchHeadless({
					resolveAsJson: false,
					url: `/structured-contents/${structuredContent?.id}/rendered-content/${contentTemplate?.id}`,
				});

				promiseStructuredContentList.push({
					instructions: await structuredComponent.text(),
					version: dxpVersion?.contentFieldValue?.data || '',
				});

				return structuredContentList;
			},
			Promise.resolve([])
		);

		setActivationInstructionsData(renderedInstructionsData);
		setIsLoadingActivationInstructions(false);
	};

	useEffect(() => {
		setIsLoadingActivationInstructions(true);
		fetchCommerceActivationsKeysInstructions();
	}, []);

	const columns = [
		{
			accessor: 'version',
			bodyClass: 'border border-0 py-4 pl-4',
			header: {
				name: i18n.translate('version'),
				styles:
					'bg-neutral-1 font-weight-bold text-neutral-8 table-cell-minw-200 py-3 pl-4',
			},
			headingTitle: true,
		},
		{
			accessor: 'instructions',
			bodyClass: 'border border-0',
			header: {
				name: i18n.translate('instructions'),
				styles:
					'bg-neutral-1 font-weight-bold text-neutral-8 table-cell-expand-smaller py-3',
			},
		},
	];

	if (!project) {
		return <ActivationKeysLayout.Skeleton />;
	}

	return (
		<ActivationKeysLayout>
			{project.dxpVersion && project.dxpVersion < '7.3' ? (
				<ActivationKeysLayout.Inputs
					accountKey={project.accountKey}
					productKey="commerce"
					productTitle="Commerce"
					sessionId={sessionId}
				/>
			) : (
				<Table
					className="cp-activation-keys-commerce-table mt-4 table-autofit"
					columns={columns}
					isLoading={isLoadingActivationInstructions}
					rows={ActivationInstructionsData.map(
						({instructions, version}) => ({
							instructions: (
								<div
									dangerouslySetInnerHTML={{
										__html: DOMPurify.sanitize(
											instructions,
											{USE_PROFILES: {html: true}}
										),
									}}
									key={version}
								></div>
							),
							version: (
								<span className="m-0 table-list-title text-neutral-7 text-paragraph">
									{version}
								</span>
							),
						})
					)}
				/>
			)}
		</ActivationKeysLayout>
	);
};

export default Commerce;
