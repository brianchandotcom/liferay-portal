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

import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import React, {useState} from 'react';

import {sub} from './utils.es';

const ItemSelectorUrl = ({eventName}) => {
	const inputName = 'urlInputReact';
	const [imgUrl, setImgUrl] = useState('');
	const [imgLoaded, setImgLoaded] = useState(false);

	const handleImageUrlChange = ({target}) => {
		setImgUrl(target.value);
		setImgLoaded(false);
	};

	const handleSubmit = event => {
		event.preventDefault();

		if (!imgLoaded) {
			return;
		}

		const eventData = {
			data: {
				returnType: 'URL',
				value: imgUrl
			}
		};

		Liferay.Util.getOpener().Liferay.fire(eventName, eventData);
		Liferay.Util.getOpener().Liferay.fire(`${eventName}AddItem`, eventData);
	};

	return (
		<>
			<form onSubmit={handleSubmit}>
				<ClayForm.Group>
					<label htmlFor={inputName}>
						{Liferay.Language.get('image-url')}
					</label>
					<ClayInput
						id={inputName}
						onChange={handleImageUrlChange}
						placeholder="http://"
						type="text"
						value={imgUrl}
					/>
					<p className="form-text">
						{sub(Liferay.Language.get('for-example-x'), [
							'http://www.liferay.com/liferay.png'
						])}
					</p>
				</ClayForm.Group>
				<ClayButton disabled={!imgLoaded} type="submit">
					{Liferay.Language.get('add')}
				</ClayButton>
			</form>
			{imgUrl && (
				<img
					className={`img-fluid mt-4 ${imgLoaded ? '' : 'invisible'}`}
					onLoad={() => setImgLoaded(true)}
					src={imgUrl}
				/>
			)}
		</>
	);
};

export default function(props) {
	return <ItemSelectorUrl {...props} />;
}
