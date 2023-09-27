/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayPanel from '@clayui/panel';
import {FrontendDataSet} from '@liferay/frontend-data-set-web';
import {
	onActionDropdownItemClick,
	openToast,
} from '@liferay/object-js-components-web';
import {createResourceURL, fetch} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import {Item} from './DefinitionOfTerms';

interface GeneralTermsProps {
	baseResourceURL: string;
}

export function GeneralTerms({baseResourceURL}: GeneralTermsProps) {
	const [generalTermsItems, setGeneralTermsItems] = useState<Item[]>([]);

	const copyGeneralTerm = ({itemData}: {itemData: Item}) => {
		navigator.clipboard.writeText(itemData.termName);

		openToast({
			message: Liferay.Language.get('term-copied-successfully'),
			type: 'success',
		});
	};

	useEffect(() => {
		Liferay.on('copyGeneralTerm', copyGeneralTerm);

		return () => {
			Liferay.detach('copyGeneralTerm');
		};
	}, []);

	useEffect(() => {
		const makeFetch = async () => {
			const response = await fetch(
				createResourceURL(baseResourceURL, {
					p_p_resource_id:
						'/notification_templates/get_general_notification_template_terms',
				}).toString()
			);

			const responseJSON = (await response.json()) as Item[];

			setGeneralTermsItems(responseJSON);
		};

		makeFetch();
	}, [baseResourceURL]);

	return (
		<ClayPanel
			collapsable
			defaultExpanded
			displayTitle={Liferay.Language.get('general-terms')}
			displayType="unstyled"
			showCollapseIcon={true}
		>
			<ClayPanel.Body>
				<FrontendDataSet
					id="GeneralTermsTable"
					items={generalTermsItems ?? []}
					itemsActions={[
						{
							href: 'copyGeneralTerm',
							id: 'copyGeneralTerm',
							label: Liferay.Language.get('copy'),
							target: 'event',
						},
					]}
					onActionDropdownItemClick={onActionDropdownItemClick}
					selectedItemsKey="termName"
					showManagementBar={false}
					showPagination={false}
					showSearch={false}
					views={[
						{
							contentRenderer: 'table',
							label: 'Table',
							name: 'table',
							schema: {
								fields: [
									{
										fieldName: 'termLabel',
										label: Liferay.Language.get('label'),
									},
									{
										fieldName: 'termName',
										label: Liferay.Language.get('term'),
									},
								],
							},
						},
					]}
				/>
			</ClayPanel.Body>
		</ClayPanel>
	);
}
