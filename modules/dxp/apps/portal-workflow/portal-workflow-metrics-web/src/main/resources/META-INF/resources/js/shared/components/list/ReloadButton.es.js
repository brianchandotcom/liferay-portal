/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
