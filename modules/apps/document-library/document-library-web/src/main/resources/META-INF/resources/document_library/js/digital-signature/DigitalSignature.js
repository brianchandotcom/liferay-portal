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

import React, {useEffect} from 'react';

import {handleCollectDigitalSignatureVisibility} from './DigitalSignatureUtil';

/**
 *
 * @description this component acts like an event listener to handle
 * CollectDigitalSignature activation
 */

const DigitalSignature = ({allowedFileExtensions, portletNamespace}) => {
	useEffect(() => {
		let eventHandler;

		Liferay.componentReady(`${portletNamespace}entries`).then(
			(searchContainer) => {
				eventHandler = searchContainer.on('rowToggled', (event) => {
					handleCollectDigitalSignatureVisibility(
						event.elements.allSelectedElements.get('value'),
						allowedFileExtensions
					);
				});
			}
		);

		return () => {
			eventHandler?.detach();
		};
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return <div />;
};

export default DigitalSignature;
