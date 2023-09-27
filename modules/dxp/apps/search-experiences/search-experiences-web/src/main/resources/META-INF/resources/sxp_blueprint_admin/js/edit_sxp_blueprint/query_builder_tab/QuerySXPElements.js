/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import {ClayTooltipProvider} from '@clayui/tooltip';
import React, {useState} from 'react';

import JSONSXPElement from '../../shared/JSONSXPElement';
import SXPElement from '../../shared/sxp_element/index';
import {SXP_ELEMENT_PREFIX} from '../../utils/constants';
import {setStorageAddSXPElementSidebar} from '../../utils/sessionStorage';
import isCustomJSONSXPElement from '../../utils/sxp_element/is_custom_json_sxp_element';

function QuerySXPElements({
	elementInstances,
	entityJSON,
	errors = [],
	isIndexCompany,
	isSubmitting,
	indexFields,
	onBlur,
	onChange,
	onDeleteSXPElement,
	onChangeAddSXPElementVisibility,
	searchableTypes,
	setFieldTouched,
	setFieldValue,
	touched = [],
}) {
	const [collapseAll, setCollapseAll] = useState(false);

	const _handleClickAddQueryElement = () => {
		setStorageAddSXPElementSidebar();

		onChangeAddSXPElementVisibility();
	};

	return (
		<div className="query-sxp-elements">
			<ClayLayout.Row className="configuration-header" justify="between">
				<ClayLayout.Col size={6}>
					{Liferay.Language.get('query-elements')}
				</ClayLayout.Col>

				<ClayLayout.Col size={6}>
					<div className="builder-actions">
						<ClayButton
							aria-label={Liferay.Language.get('collapse-all')}
							className="collapse-button"
							displayType="unstyled"
							onClick={() => setCollapseAll(!collapseAll)}
						>
							{collapseAll
								? Liferay.Language.get('expand-all')
								: Liferay.Language.get('collapse-all')}
						</ClayButton>

						<ClayTooltipProvider>
							<ClayButton
								aria-label={Liferay.Language.get(
									'add-query-element'
								)}
								displayType="primary"
								monospaced
								onClick={_handleClickAddQueryElement}
								small
								title={Liferay.Language.get(
									'add-query-element'
								)}
							>
								<ClayIcon symbol="plus" />
							</ClayButton>
						</ClayTooltipProvider>
					</div>
				</ClayLayout.Col>
			</ClayLayout.Row>

			{!elementInstances.length ? (
				<div className="sheet">
					<div className="selected-sxp-elements-empty-text">
						{Liferay.Language.get(
							'add-elements-to-optimize-search-results-for-your-use-cases'
						)}
					</div>
				</div>
			) : (
				elementInstances.map(
					({id, sxpElement, uiConfigurationValues}, index) => {
						return isCustomJSONSXPElement(uiConfigurationValues) ? (
							<JSONSXPElement
								collapseAll={collapseAll}
								error={errors[index]}
								id={id}
								index={index}
								isSubmitting={isSubmitting}
								key={id}
								onDeleteSXPElement={onDeleteSXPElement}
								prefixedId={`${SXP_ELEMENT_PREFIX.QUERY}-${index}`}
								setFieldTouched={setFieldTouched}
								setFieldValue={setFieldValue}
								sxpElement={sxpElement}
								touched={touched[index]}
								uiConfigurationValues={uiConfigurationValues}
							/>
						) : (
							<SXPElement
								collapseAll={collapseAll}
								entityJSON={entityJSON}
								error={errors[index]}
								id={id}
								index={index}
								indexFields={indexFields}
								isIndexCompany={isIndexCompany}
								isSubmitting={isSubmitting}
								key={id}
								onBlur={onBlur}
								onChange={onChange}
								onDeleteSXPElement={onDeleteSXPElement}
								prefixedId={`${SXP_ELEMENT_PREFIX.QUERY}-${index}`}
								searchableTypes={searchableTypes}
								setFieldTouched={setFieldTouched}
								setFieldValue={setFieldValue}
								sxpElement={sxpElement}
								touched={touched[index]}
								uiConfigurationValues={uiConfigurationValues}
							/>
						);
					}
				)
			)}
		</div>
	);
}

export default React.memo(QuerySXPElements);
