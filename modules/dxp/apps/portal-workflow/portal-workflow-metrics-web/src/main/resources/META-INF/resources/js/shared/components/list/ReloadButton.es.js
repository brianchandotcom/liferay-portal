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
import React from 'react';

export default class ReloadButton extends React.Component {
	reloadPage() {
		location.reload();
	}

	render() {
		return (
			<ClayButton
				displayType="link"
				onClick={this.reloadPage.bind(this)}
				small
			>
				{Liferay.Language.get('reload-page')}
			</ClayButton>
		);
	}
}
