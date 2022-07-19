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

import 'leaflet/dist/leaflet.css';
import ClayIcon from '@clayui/icon';
import React, {useCallback, useState} from 'react';

import {FieldBase} from '../FieldBase/ReactFieldBase.es';
import {MAP_PROVIDER, useGeolocation} from './useGeolocation.es';

const geolocateTitle = Liferay.Language.get('geolocate');
const pathThemeImages = Liferay.ThemeDisplay.getPathThemeImages();

class NoRender extends React.Component {
	shouldComponentUpdate() {
		return false;
	}

	render() {
		return <div {...this.props} />;
	}
}

const Geolocation = ({
	disabled,
	googleMapsAPIKey,
	instanceId,
	mapProviderKey,
	name,
	onChange,
	value,
	viewMode,
	...otherProps
}) => {
	const [address, setAddress] = useState();

	const handleChange = useCallback(
		({newVal: {address, location}}) => {
			setAddress(address);
			onChange(JSON.stringify(location));
		},
		[onChange, setAddress]
	);

	useGeolocation({
		disabled,
		googleMapsAPIKey,
		instanceId,
		mapProviderKey,
		name,
		onChange: handleChange,
		value,
		viewMode,
	});

	return (
		<div {...otherProps} className="ddm-geolocation field-labels-inline">
			{!disabled || viewMode ? (
				<div>
					<div>
						<ClayIcon symbol="geolocation" />

						{address}
					</div>

					<dl>
						<dt className="text-capitalize"></dt>

						<dd>
							<NoRender
								className="lfr-map"
								id={`map_${instanceId}`}
								style={{height: '280px'}}
							/>

							<input
								id={`input_value_${instanceId}`}
								name={name}
								type="hidden"
							/>
						</dd>
					</dl>
				</div>
			) : (
				<img
					alt={Liferay.Language.get('geolocation')}
					className="w-100"
					src={`${pathThemeImages}/common/geolocation.png`}
					style={{maxWidth: '150px'}}
					title={geolocateTitle}
				/>
			)}
		</div>
	);
};

const Main = ({
	googleMapsAPIKey,
	instanceId,
	mapProviderKey = MAP_PROVIDER.openStreetMap,
	name,
	onChange,
	readOnly,
	value,
	viewMode,
	...otherProps
}) => (
	<FieldBase name={name} readOnly={readOnly} {...otherProps}>
		<Geolocation
			disabled={readOnly}
			googleMapsAPIKey={googleMapsAPIKey}
			instanceId={instanceId}
			mapProviderKey={mapProviderKey}
			name={name}
			onChange={(value) => onChange({}, value)}
			value={value}
			viewMode={viewMode}
		/>
	</FieldBase>
);

Main.displayName = 'Geolocation';

export default Main;
