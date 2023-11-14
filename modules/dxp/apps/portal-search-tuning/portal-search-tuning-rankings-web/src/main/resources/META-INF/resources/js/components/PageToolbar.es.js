/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayLink from '@clayui/link';
import {ManagementToolbar} from 'frontend-js-components-web';
import PropTypes from 'prop-types';
import React, {Component} from 'react';

class PageToolbar extends Component {
	static props = {
		onCancel: PropTypes.string.isRequired,
		onChangeActive: PropTypes.func,
		onPublish: PropTypes.func.isRequired,
		onSaveAsDraft: PropTypes.func,
		resultRankingStatus: PropTypes.string.isRequired,
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
			resultRankingStatus,
			submitDisabled,
		} = this.props;

		return (
			<ManagementToolbar.Container
				aria-label={Liferay.Language.get('save')}
				className="page-toolbar-root"
			>
				{resultRankingStatus !== 'not-applicable' && (
					<ManagementToolbar.ItemList>
						<ManagementToolbar.Item>
							<label
								className="toggle-switch"
								htmlFor="active-switch-input"
							>
								<input
									checked={resultRankingStatus === 'active'}
									className="toggle-switch-check"
									id="active-switch-input"
									onChange={onChangeActive}
									type="checkbox"
								/>

								<span className="toggle-switch-bar">
									<span className="toggle-switch-handle"></span>
								</span>

								<span className="toggle-switch-text-right">
									{resultRankingStatus === 'active'
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

					{onSaveAsDraft && resultRankingStatus !== 'not-applicable' && (
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

					{resultRankingStatus !== 'not-applicable' && (
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
