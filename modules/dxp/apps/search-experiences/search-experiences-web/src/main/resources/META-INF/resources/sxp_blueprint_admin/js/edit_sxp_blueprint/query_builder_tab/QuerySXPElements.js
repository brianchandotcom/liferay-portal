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
import ClayLayout from '@clayui/layout';
import {ClayTooltipProvider} from '@clayui/tooltip';
import React, {useState} from 'react';

import JSONSXPElement from '../../shared/JSONSXPElement';
import SXPElement from '../../shared/sxp_element/index';
import {SXP_ELEMENT_PREFIX} from '../../utils/constants';
import {setItemAddSXPElementSidebar} from '../../utils/sessionStorage';
import {isCustomJSONSXPElement} from '../../utils/utils';

function QuerySXPElements({
	elementInstances,
	entityJSON,
	errors = [],
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
		setItemAddSXPElementSidebar();

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

			{elementInstances.length === 0 ? (
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
