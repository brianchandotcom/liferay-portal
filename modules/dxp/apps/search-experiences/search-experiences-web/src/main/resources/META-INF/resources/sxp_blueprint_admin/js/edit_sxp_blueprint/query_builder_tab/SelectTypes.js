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
		a.displayName.localeCompare(b.displayName, locale.replace('_', '-'))
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
				onFetchSearchableTypes={onFetchSearchableTypes}
				onFrameworkConfigChange={onFrameworkConfigChange}
				searchableTypes={searchableTypesSorted}
				selectedTypes={selectedTypes}
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
