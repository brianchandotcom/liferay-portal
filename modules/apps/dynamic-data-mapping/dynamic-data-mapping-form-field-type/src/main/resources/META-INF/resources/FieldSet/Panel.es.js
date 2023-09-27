/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import './Panel.scss';

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayPanel from '@clayui/panel';
import classNames from 'classnames';
import {
	EVENT_TYPES as CORE_EVENT_TYPES,
	useForm,
} from 'data-engine-js-components-web';
import React from 'react';

const PanelHeader = ({
	name,
	readOnly,
	repeatable,
	showRepeatableRemoveButton,
	title,
}) => {
	const dispatch = useForm();

	return (
		<div className="ddm-form-field-panel-header-container">
			<label className="text-uppercase">{title}</label>

			{repeatable && (
				<span className="actions collapse-icon-options">
					<div className="lfr-ddm-form-field-repeatable-toolbar">
						{showRepeatableRemoveButton && (
							<ClayButton
								className="ddm-form-field-repeatable-delete-button lfr-portal-tooltip p-0"
								disabled={readOnly}
								onClick={(event) => {
									event.stopPropagation();

									dispatch({
										payload: name,
										type: CORE_EVENT_TYPES.FIELD.REMOVED,
									});
								}}
								small
								title={Liferay.Language.get('remove')}
							>
								<ClayIcon symbol="hr" />
							</ClayButton>
						)}

						<ClayButton
							className="ddm-form-field-repeatable-add-button lfr-portal-tooltip p-0"
							disabled={readOnly}
							onClick={(event) => {
								event.stopPropagation();

								dispatch({
									payload: name,
									type: CORE_EVENT_TYPES.FIELD.REPEATED,
								});
							}}
							small
							title={Liferay.Language.get('duplicate')}
						>
							<ClayIcon symbol="plus" />
						</ClayButton>
					</div>
				</span>
			)}
		</div>
	);
};

const Panel = ({
	children,
	name,
	readOnly,
	repeatable,
	showRepeatableRemoveButton,
	title,
}) => {
	return (
		<ClayPanel
			className={classNames(
				'collapsable-panel',
				'panel',
				'panel-unstyled'
			)}
			collapsable
			defaultExpanded
			displayTitle={
				<PanelHeader
					name={name}
					readOnly={readOnly}
					repeatable={repeatable}
					showRepeatableRemoveButton={showRepeatableRemoveButton}
					title={title}
				/>
			}
			role="tablist"
		>
			<>
				<ClayPanel.Body>{children}</ClayPanel.Body>
			</>
		</ClayPanel>
	);
};

export default Panel;
