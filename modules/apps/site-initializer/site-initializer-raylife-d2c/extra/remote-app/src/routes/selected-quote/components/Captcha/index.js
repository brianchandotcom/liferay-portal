/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {useEffect, useRef} from 'react';
import {getImage} from '../../services/Captcha';

const Captcha = ({captchaValue, setCaptchaValue}) => {
	const captchaRef = useRef();

	const getCaptchaImage = () => {
		getImage().then((response) => {
			if (captchaRef.current) {
				captchaRef.current.src = response?.request?.responseURL;
			}
		});
	};

	useEffect(() => {
		getCaptchaImage();
	}, []);

	return (
		<div className="captcha-container mt-4">
			<div className="d-flex flex-column">
				<div className="align-items-center d-flex image-captcha mb-3">
					<img ref={captchaRef} />

					<ClayIcon
						className="ml-2 reload-captcha text-neutral-5"
						onClick={() => {
							getCaptchaImage();
						}}
						symbol="change"
					/>
				</div>

				<div
					className={classNames(
						'form-condensed form-group input-content',
						{
							filled: captchaValue,
						}
					)}
				>
					<ClayInput
						autoComplete="off"
						className="w-100"
						id="input-captcha"
						onChange={(event) => {
							setCaptchaValue(event.target.value);
						}}
						required
						type="text"
						value={captchaValue}
					/>

					<label htmlFor="input-captcha">
						What code is in the image?
					</label>
				</div>
			</div>
		</div>
	);
};

export default Captcha;
