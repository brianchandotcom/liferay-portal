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

import classNames from 'classnames';
import {useState} from 'react';

import Carrousel from './Carrousel/Carrousel';

import './index.scss';
enum NavVehicleLabel {
	Exterior = 'Exterior',
	Interior = 'Interior',
}

const DamageSummary = () => {
	const [active, setActive] = useState(NavVehicleLabel.Exterior);

	return (
		<>
			<div className="d-flex damage-summary-container">
				<div className="w-50">
					<div className="p-5">
						<p className="list-title">Condition of Your Vehicle</p>

						<p className="list-text">Good to drive</p>
					</div>

					<div className="col d-flex">
						<div className="col-3 ml-5">
							<p
								className={classNames('cursor-pointer pl-5', {
									'vehicle-link-active':
										active === NavVehicleLabel.Exterior,
								})}
								onClick={() =>
									setActive(NavVehicleLabel.Exterior)
								}
								vehicle-link-active
							>
								{NavVehicleLabel.Exterior}
							</p>

							<p
								className={classNames('cursor-pointer pl-5', {
									'vehicle-link-active':
										active === NavVehicleLabel.Interior,
								})}
								onClick={() =>
									setActive(NavVehicleLabel.Interior)
								}
							>
								{NavVehicleLabel.Interior}
							</p>
						</div>

						<div className="pb-5">
							{active === NavVehicleLabel.Exterior ? (
								<img src="http://placekitten.com/400/200" />
							) : (
								<img src="http://placekitten.com/400/210"></img>
							)}
						</div>
					</div>
				</div>

				<div className="list-title pt-5 w-50">
					<p>Damage Pictures</p>

					<Carrousel />
				</div>
			</div>
		</>
	);
};

export default DamageSummary;
