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
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {useState} from 'react';

import Galerry from '../../../../../../common/components/Carrousel/Galerry';
import {getWebDavUrl} from '../../../../../../common/utils/webdav';

import './index.scss';

enum NavVehicleLabel {
	Exterior = 'Exterior',
	Interior = 'Interior',
}

const images = [
	`${getWebDavUrl()}/driver_side_damage_rear.svg`,
	`${getWebDavUrl()}/driver_side_damage.svg`,
];

const DamageSummary = () => {
	const [active, setActive] = useState(NavVehicleLabel.Exterior);

	return (
		<>
			<div className="d-flex damage-summary-container">
				<div className="container-left-side w-50">
					<div className="pl-5 pt-5">
						<p className="font-weight-semi-bold list-title">
							Condition of Your Vehicle
						</p>

						<p className="font-weight-bold h6 mb-4 text-brand-primary-darken-5">
							Good to drive
						</p>
					</div>

					<div className="align-items-center col d-flex flex-wrap">
						<div className="mx-5">
							<div
								className={classNames(
									'cursor-pointer vehicle-link',
									{
										'vehicle-link-active':
											active === NavVehicleLabel.Exterior,
									}
								)}
								onClick={() =>
									setActive(NavVehicleLabel.Exterior)
								}
							>
								<p className="ml-4">
									{NavVehicleLabel.Exterior}
								</p>
							</div>

							<div
								className={classNames(
									'cursor-pointer vehicle-link',
									{
										'vehicle-link-active':
											active === NavVehicleLabel.Interior,
									}
								)}
								onClick={() =>
									setActive(NavVehicleLabel.Interior)
								}
							>
								<p className="ml-4">
									{NavVehicleLabel.Interior}
								</p>
							</div>
						</div>

						<div className="pb-5 pl-5 vehicle-damage">
							<img
								src={`${getWebDavUrl()}/${
									active === NavVehicleLabel.Exterior
										? 'auto_exterior.svg'
										: 'auto_interior.svg'
								}`}
							/>
						</div>
					</div>
				</div>

				<div className="container-right-side font-weight-semi-bold list-title mb-1 pl-5 pt-5 w-50">
					<p className="pb-3">Damage Pictures</p>

					<div className="d-flex">
						{!!images.length && (
							<Galerry
								images={images}
								size={{
									height: '150px',
									width: '255px',
								}}
							/>
						)}

						<ClayButton className="btn-add pl-3" displayType="link">
							<span className="inline-item inline-item-before">
								<ClayIcon symbol="plus" />
							</span>
							Add
						</ClayButton>
					</div>
				</div>
			</div>
		</>
	);
};

export default DamageSummary;
