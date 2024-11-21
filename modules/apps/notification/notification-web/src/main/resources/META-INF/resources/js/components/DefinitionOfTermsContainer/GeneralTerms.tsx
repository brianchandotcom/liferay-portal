/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayPanel from '@clayui/panel';
import {FrontendDataSet} from '@liferay/frontend-data-set-web';
import {createResourceURL, fetch} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import copyTerm from '../../util/copyTerm';
import {Item} from './DefinitionOfTerms';

interface GeneralTermsProps {
	baseResourceURL: string;
}

export function GeneralTerms({baseResourceURL}: GeneralTermsProps) {
	const [generalTermsItems, setGeneralTermsItems] = useState<Item[]>([]);

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
							href: 'copyTerm',
							icon: 'copy',
							id: 'copyTerm',
							label: Liferay.Language.get('copy'),
							onClick: copyTerm,
							target: 'event',
						},
					]}
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
