/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayTable from '@clayui/table';
import React, {useContext} from 'react';

import ThemeContext from '../../shared/ThemeContext';
import SearchableTypesModal from './SearchableTypesModal';

function SelectTypes({
	onFrameworkConfigChange,
	onFetchSearchableTypes,
	searchableTypes = [],
	selectedTypes = [],
}) {
	const {locale} = useContext(ThemeContext);

	const searchableTypesSorted = searchableTypes.sort((a, b) =>
		a.displayName.localeCompare(b.displayName, locale.replaceAll('_', '-'))
	);

	const _handleDelete = (type) => () => {
		const newSelected = selectedTypes.filter((item) => item !== type);

		onFrameworkConfigChange({
			searchableAssetTypes: newSelected,
		});
	};

	return (
		<>
			<SearchableTypesModal
				initialSelectedTypes={selectedTypes}
				onFetchSearchableTypes={onFetchSearchableTypes}
				onFrameworkConfigChange={onFrameworkConfigChange}
				searchableTypes={searchableTypesSorted}
			>
				<ClayButton
					className="select-types-button"
					displayType="secondary"
					small
				>
					{Liferay.Language.get('select-asset-types')}
				</ClayButton>
			</SearchableTypesModal>

			{!!selectedTypes.length && (
				<ClayTable>
					<ClayTable.Body>
						{searchableTypesSorted
							.filter(({className}) =>
								selectedTypes.includes(className)
							)
							.map(({className, displayName}) => (
								<ClayTable.Row key={className}>
									<ClayTable.Cell expanded headingTitle>
										{displayName}
									</ClayTable.Cell>

									<ClayTable.Cell>
										<ClayButton
											aria-label={Liferay.Language.get(
												'delete'
											)}
											className="secondary"
											displayType="unstyled"
											onClick={_handleDelete(className)}
											small
										>
											<ClayIcon symbol="times" />
										</ClayButton>
									</ClayTable.Cell>
								</ClayTable.Row>
							))}
					</ClayTable.Body>
				</ClayTable>
			)}
		</>
	);
}

export default React.memo(SelectTypes);
