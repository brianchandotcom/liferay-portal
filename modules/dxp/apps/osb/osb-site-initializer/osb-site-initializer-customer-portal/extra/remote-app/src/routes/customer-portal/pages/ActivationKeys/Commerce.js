import {useQuery} from '@apollo/client';
import DOMPurify from 'dompurify';
import {useEffect, useState} from 'react';
import Table from '../../../../common/components/Table';
import {fetchHeadless} from '../../../../common/services/liferay/api';
import {getKoroneikiAccounts} from '../../../../common/services/liferay/graphql/queries';
import ActivationKeysLayout from '../../components/ActivationKeysLayout';

const Commerce = ({accountKey}) => {
	const [
		ActivationInstructionsData,
		setActivationInstructionsData,
	] = useState([]);
	const [
		isLoadingActivationInstructions,
		setIsLoadingActivationInstructions,
	] = useState(false);
	const {data, loading} = useQuery(getKoroneikiAccounts, {
		variables: {
			filter: `accountKey eq '${accountKey}'`,
		},
	});

	const dxpVersion = data?.c?.koroneikiAccounts?.items[0]?.dxpVersion;

	const fetchCommerceActivationsKeysInstructions = async () => {
		const templateName = 'COMMERCE ACTIVATION INSTRUCTIONS';

		const siteGroupId = Liferay.ThemeDisplay.getSiteGroupId();

		const structuredContentFolders = await fetchHeadless({
			url: `/sites/${siteGroupId}/structured-content-folders`,
		});

		const {id: commerceActivationInstructionsFolderID} =
			structuredContentFolders.items.find(
				({name}) => name === templateName
			) || {};

		const contentTemplates = await fetchHeadless({
			url: `/sites/${siteGroupId}/content-templates`,
		});

		const contentTemplate = contentTemplates.items.find(
			(template) => template.name === templateName
		);

		const structuredContents = await fetchHeadless({
			url: `/structured-content-folders/${commerceActivationInstructionsFolderID}/structured-contents`,
		});

		const renderedInstructionsData = await structuredContents.items.reduce(
			async (structuredContentList, structuredContent) => {
				const promiseStructuredContentList = await structuredContentList;

				const dxpVersion =
					structuredContent.contentFields.find(
						({label}) => label === 'DXP Version'
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
				name: 'Version',
				styles:
					'bg-neutral-1 font-weight-bold text-neutral-8 table-cell-minw-200 py-3 pl-4',
			},
			headingTitle: true,
		},
		{
			accessor: 'instructions',
			bodyClass: 'border border-0',
			header: {
				name: 'Instructions',
				styles:
					'bg-neutral-1 font-weight-bold text-neutral-8 table-cell-expand-smaller py-3',
			},
		},
	];

	if (loading) {
		return <ActivationKeysLayout.Skeleton />;
	}

	return (
		<ActivationKeysLayout>
			{dxpVersion && dxpVersion !== '7.3' ? (
				<ActivationKeysLayout.Inputs
					accountKey={accountKey}
					productKey="commerce"
					productTitle="Commerce"
				/>
			) : (
				<Table
					columns={columns}
					isLoading={isLoadingActivationInstructions}
					itemsPerPage={3}
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
							version,
						})
					)}
				/>
			)}
		</ActivationKeysLayout>
	);
};

export default Commerce;
