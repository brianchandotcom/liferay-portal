/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
		navigator.clipboard.writeText(itemData.term);

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
						'/notification_templates/notification_template_general_terms',
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
			defaultExpanded={false}
			displayTitle={Liferay.Language.get('general-terms')}
			displayType="secondary"
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
										fieldName: 'name',
										label: Liferay.Language.get('name'),
									},
									{
										fieldName: 'term',
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
