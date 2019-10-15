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
import PropTypes from 'prop-types';
import React, {Component} from 'react';

class PageToolbar extends Component {
	static props = {
		onCancel: PropTypes.string.isRequired,
		onPublish: PropTypes.func.isRequired,
		onSaveAsDraft: PropTypes.func,
		submitDisabled: PropTypes.bool
	};

	static defaultProps = {
		submitDisabled: false
	};

	render() {
		const {onCancel, onPublish, onSaveAsDraft, submitDisabled} = this.props;

		return (
			<nav
				aria-label={Liferay.Language.get('save')}
				className="page-toolbar-root tbar upper-tbar"
			>
				<div className="container-fluid container-fluid-max-xl">
					<ul className="tbar-nav">
						<li className="tbar-item tbar-item-expand" />

						<li className="tbar-item">
							<ClayLink
								displayType="secondary"
								href={onCancel}
								outline="secondary"
							>
								{Liferay.Language.get('cancel')}
							</ClayLink>
						</li>

						{onSaveAsDraft && (
							<li className="tbar-item">
								<ClayButton
									displayType="secondary"
									onClick={onSaveAsDraft}
									small
								>
									{Liferay.Language.get('save-as-draft')}
								</ClayButton>
							</li>
						)}

						<li className="tbar-item">
							<ClayButton
								disabled={submitDisabled}
								onClick={onPublish}
								small
								type="submit"
							>
								{Liferay.Language.get('save')}
							</ClayButton>
						</li>
					</ul>
				</div>
			</nav>
		);
	}
}

export default PageToolbar;
