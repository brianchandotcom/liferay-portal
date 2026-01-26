/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayInput, ClayToggle} from '@clayui/form';
import ClayLayout from '@clayui/layout';
import {
	IBaseFilterState,
	IFDSState,
	ISelectionFilterState,
} from '@liferay/frontend-data-set-web';
import {useLiferayState} from '@liferay/frontend-js-state-web/react';
import React from 'react';

import {advancedFDSAtom} from '../utils/atoms';

/**
 * This fragment highlights sync with FDS, where contexts are joined through
 * the atom object. Atom is imported here, and passed to FDS as a prop.
 *
 * This is the recommended method of wiring, as it ensures type safety.
 */
const AdvancedFilters = () => {
	const [advancedFDSState, setAdvancedFDSState] =
		useLiferayState<IFDSState>(advancedFDSAtom);

	const colorFilter = advancedFDSState.filters.find(
		(filter: IBaseFilterState) => filter.id === 'color'
	);

	return (
		<ClayLayout.ContainerFluid className="pt-2">
			<ClayInput.Group className="pt-2">
				Apply Color filter in Advanced tab of FDS Sample
			</ClayInput.Group>

			<ClayInput.Group className="pt-2">
				<ClayInput.GroupItem>
					<ClayToggle
						aria-label="Active"
						data-qa-id="activeFiltersFDSSampleToggle"
						disabled={Boolean(!colorFilter?.selectedData)}
						label="Active"
						onToggle={(value) => {
							setAdvancedFDSState({
								...advancedFDSState,
								filters: advancedFDSState.filters.map(
									(filter: IBaseFilterState) => {
										if (filter.id === 'color') {
											return {...filter, active: value};
										}

										return filter;
									}
								),
							});
						}}
						toggled={colorFilter?.active ?? false}
					/>
				</ClayInput.GroupItem>

				<ClayInput.GroupItem>
					<ClayToggle
						aria-label="Exclude"
						data-qa-id="excludeFiltersFDSSampleToggle"
						disabled={Boolean(!colorFilter?.selectedData)}
						label="Exclude"
						onToggle={(value) => {
							setAdvancedFDSState({
								...advancedFDSState,
								filters: advancedFDSState.filters.map(
									(filter: IBaseFilterState) => {
										if (filter.id === 'color') {
											return {
												...filter,
												selectedData: {
													...filter.selectedData,
													exclude: value,
												},
											} as ISelectionFilterState;
										}

										return filter;
									}
								),
							});
						}}
						toggled={
							(colorFilter?.selectedData?.exclude as boolean) ??
							false
						}
					/>
				</ClayInput.GroupItem>
			</ClayInput.Group>
		</ClayLayout.ContainerFluid>
	);
};

export default AdvancedFilters;
