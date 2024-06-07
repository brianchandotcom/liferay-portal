/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayLink from '@clayui/link';
import {ManagementToolbar} from 'frontend-js-components-web';
import PropTypes from 'prop-types';
import React, {Component} from 'react';

import {STATUS_TYPES} from '../utils/constants.es';

class PageToolbar extends Component {
	static props = {
		onCancel: PropTypes.string.isRequired,
		onChangeActive: PropTypes.func,
		onPublish: PropTypes.func.isRequired,
		onSaveAsDraft: PropTypes.func,
		status: PropTypes.string.isRequired,
		submitDisabled: PropTypes.bool,
	};

	static defaultProps = {
		submitDisabled: false,
	};

	render() {
		const {
			onCancel,
			onChangeActive,
			onPublish,
			onSaveAsDraft,
			status,
			submitDisabled,
		} = this.props;

		return (
			<ManagementToolbar.Container
				aria-label={Liferay.Language.get('save')}
				className="page-toolbar-root"
			>
				{status !== STATUS_TYPES.NOT_APPLICABLE && (
					<ManagementToolbar.ItemList>
						<ManagementToolbar.Item>
							<label
								className="toggle-switch"
								htmlFor="active-switch-input"
							>
								<input
									checked={status === STATUS_TYPES.ACTIVE}
									className="toggle-switch-check"
									id="active-switch-input"
									onChange={onChangeActive}
									type="checkbox"
								/>

								<span className="toggle-switch-bar">
									<span className="toggle-switch-handle"></span>
								</span>

								<span className="toggle-switch-text-right">
									{status === STATUS_TYPES.ACTIVE
										? Liferay.Language.get('active')
										: Liferay.Language.get('inactive')}
								</span>
							</label>
						</ManagementToolbar.Item>
					</ManagementToolbar.ItemList>
				)}

				<ManagementToolbar.ItemList expand></ManagementToolbar.ItemList>

				<ManagementToolbar.ItemList>
					<ManagementToolbar.Item>
						<ClayLink
							displayType="secondary"
							href={onCancel}
							outline="secondary"
						>
							{Liferay.Language.get('cancel')}
						</ClayLink>
					</ManagementToolbar.Item>

					{onSaveAsDraft &&
						status !== STATUS_TYPES.NOT_APPLICABLE && (
							<ManagementToolbar.Item>
								<ClayButton
									displayType="secondary"
									onClick={onSaveAsDraft}
									small
								>
									{Liferay.Language.get('save-as-draft')}
								</ClayButton>
							</ManagementToolbar.Item>
						)}

					{status === STATUS_TYPES.NOT_APPLICABLE ? (
						<ManagementToolbar.Item>
							<ClayButton
								className="link-outline-secondary"
								displayType="secondary"
								onClick={onPublish}
								small
								type="submit"
							>
								{Liferay.Language.get('delete')}
							</ClayButton>
						</ManagementToolbar.Item>
					) : (
						<ManagementToolbar.Item>
							<ClayButton
								disabled={submitDisabled}
								onClick={onPublish}
								small
								type="submit"
							>
								{Liferay.Language.get('save')}
							</ClayButton>
						</ManagementToolbar.Item>
					)}
				</ManagementToolbar.ItemList>
			</ManagementToolbar.Container>
		);
	}
}

export default PageToolbar;
