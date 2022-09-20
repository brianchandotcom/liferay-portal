/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {getWebDavUrl} from '../../../../../../../../extra/remote-app/src/common/utils/webdav';

const DrivingUnderTheInfluence = () => {
	return (
		<div className="align-items-center citation d-flex justify-content-center text-center">
			<div>
				<img
					className="mr-2"
					src={`${getWebDavUrl()}/${'exclamation_icon.svg'}`}
				/>

				<h2>Sorry, we cannot proceed with a quote at this time.</h2>

				<p className="text-neutral-7 text-paragraph">
					Further description of why we cannot proceed goes here.
				</p>
			</div>
		</div>
	);
};
export default DrivingUnderTheInfluence;
