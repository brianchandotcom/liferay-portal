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

import React from 'react';

import DetailsBox from './DetailsBox.es';
import PictureBox from './PictureBox.es';

function AreaViewer() {
	return (
		<div className="row">
			<div className="col">
				<PictureBox />
			</div>
			<div className="col col-sm-4">
				<DetailsBox />
			</div>
		</div>
	);
}

export default AreaViewer;
