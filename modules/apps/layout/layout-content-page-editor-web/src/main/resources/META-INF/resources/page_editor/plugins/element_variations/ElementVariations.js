/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {Option, Picker} from '@clayui/core';
import ClayEmptyState from '@clayui/empty-state';
import ClayLayout from '@clayui/layout';
import ClayToolbar from '@clayui/toolbar';
import {useId} from 'frontend-js-components-web';
import {navigate} from 'frontend-js-web';
import React, {useState} from 'react';

import './ElementVariations.scss';

const EXPERIENCES = [{label: Liferay.Language.get('default'), value: '0'}];

const ExperienceTrigger = React.forwardRef(({children, ...otherProps}, ref) => (
	<ClayButton
		className="form-control form-control-select form-control-sm text-left"
		displayType="secondary"
		ref={ref}
		{...otherProps}
	>
		{children}
	</ClayButton>
));

export default function ElementVariations({redirect}) {
	const experienceId = useId();
	const [experienceKey, setExperienceKey] = useState('0');

	return (
		<div className="d-flex element-variations flex-column">
			<ClayToolbar className="bg-white">
				<ClayLayout.ContainerFluid size={false}>
					<ClayToolbar.Nav>
						<ClayToolbar.Item expand />

						<ClayToolbar.Item>
							<ClayButton
								displayType="secondary"
								onClick={() => navigate(redirect)}
								size="sm"
							>
								{Liferay.Language.get('cancel')}
							</ClayButton>
						</ClayToolbar.Item>

						<ClayToolbar.Item>
							<ClayButton displayType="primary" size="sm">
								{Liferay.Language.get('publish-variant')}
							</ClayButton>
						</ClayToolbar.Item>
					</ClayToolbar.Nav>
				</ClayLayout.ContainerFluid>
			</ClayToolbar>

			<div className="d-flex element-variations__content flex-grow-1">
				<iframe
					className="border-0 flex-grow-1 h-100 w-100"
					src="https://example.com"
					title={Liferay.Language.get('element-variations')}
				/>

				<div className="bg-white border-left d-flex element-variations__sidebar flex-column flex-shrink-0">
					<div className="border-bottom flex-shrink-0 px-3 py-3">
						<span className="font-weight-bold">
							{Liferay.Language.get('element-variations')}
						</span>
					</div>

					<div className="flex-grow-1">
						<div className="form-group p-3">
							<label htmlFor={experienceId}>
								{Liferay.Language.get('experience')}
							</label>

							<Picker
								aria-label={Liferay.Language.get('experience')}
								as={ExperienceTrigger}
								id={experienceId}
								items={EXPERIENCES}
								onSelectionChange={(selection) =>
									setExperienceKey(String(selection))
								}
								selectedKey={experienceKey}
							>
								{(item) => (
									<Option key={item.value}>
										{item.label}
									</Option>
								)}
							</Picker>
						</div>

						<div className="align-items-center d-flex flex-column justify-content-between px-3">
							<ClayEmptyState
								className="mb-0"
								description={Liferay.Language.get(
									'you-can-create-page-elements-variations-based-on-audiences'
								)}
								imgSrc={`${Liferay.ThemeDisplay.getPathThemeImages()}/states/empty_state.svg`}
								small
								title={Liferay.Language.get(
									'no-variations-yet'
								)}
							/>

							<ClayButton
								className="mt-2"
								displayType="secondary"
								size="sm"
							>
								{Liferay.Language.get('new-variation')}
							</ClayButton>
						</div>
					</div>
				</div>
			</div>
		</div>
	);
}
