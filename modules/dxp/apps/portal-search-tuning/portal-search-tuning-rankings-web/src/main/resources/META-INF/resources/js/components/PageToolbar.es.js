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
import ClayLink from '@clayui/link';
import ClayManagementToolbar from '@clayui/management-toolbar';
import PropTypes from 'prop-types';
import React, {Component} from 'react';

class PageToolbar extends Component {
	static props = {
		active: PropTypes.bool,
		onCancel: PropTypes.string.isRequired,
		onChangeActive: PropTypes.func,
		onPublish: PropTypes.func.isRequired,
		onSaveAsDraft: PropTypes.func,
		submitDisabled: PropTypes.bool
	};

	static defaultProps = {
		submitDisabled: false
	};

	render() {
		const {
			active,
			onCancel,
			onChangeActive,
			onPublish,
			onSaveAsDraft,
			submitDisabled
		} = this.props;

		return (
			<ClayManagementToolbar
				aria-label={Liferay.Language.get('save')}
				className="page-toolbar-root"
			>
				<ClayManagementToolbar.ItemList>
					<ClayManagementToolbar.Item>
						<label
							className="toggle-switch"
							htmlFor="active-switch-input"
						>
							<input
								checked={active}
								className="toggle-switch-check"
								id="active-switch-input"
								onChange={onChangeActive}
								type="checkbox"
							/>
							<span className="toggle-switch-bar">
								<span className="toggle-switch-handle"></span>
							</span>
							<span className="toggle-switch-text-right">
								{active
									? Liferay.Language.get('active')
									: Liferay.Language.get('inactive')}
							</span>
						</label>
					</ClayManagementToolbar.Item>
				</ClayManagementToolbar.ItemList>

				<ClayManagementToolbar.ItemList
					expand
				></ClayManagementToolbar.ItemList>

				<ClayManagementToolbar.ItemList>
					<ClayManagementToolbar.Item>
						<ClayLink
							displayType="secondary"
							href={onCancel}
							outline="secondary"
						>
							{Liferay.Language.get('cancel')}
						</ClayLink>
					</ClayManagementToolbar.Item>

					{onSaveAsDraft && (
						<ClayManagementToolbar.Item>
							<ClayButton
								displayType="secondary"
								onClick={onSaveAsDraft}
								small
							>
								{Liferay.Language.get('save-as-draft')}
							</ClayButton>
						</ClayManagementToolbar.Item>
					)}

					<ClayManagementToolbar.Item>
						<ClayButton
							disabled={submitDisabled}
							onClick={onPublish}
							small
							type="submit"
						>
							{Liferay.Language.get('save')}
						</ClayButton>
					</ClayManagementToolbar.Item>
				</ClayManagementToolbar.ItemList>
			</ClayManagementToolbar>
		);
	}
}

export default PageToolbar;
