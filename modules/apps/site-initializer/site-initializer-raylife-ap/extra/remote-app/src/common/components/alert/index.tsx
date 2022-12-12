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

import {ClayButtonWithIcon} from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayTable from '@clayui/table';
import {useState} from 'react';

type AlertProps = {
	claim?: String;
	claimNumber: Number;
	index?: Number;
	setVisible?: boolean;
	visible?: boolean;
};
const Alert: React.FC<AlertProps> = ({claimNumber}) => {
	const [visible, setVisible] = useState<Boolean>(true);

	return (
		<>
			{visible && (
				<ClayTable.Row className="info-row">
					<ClayTable.Cell className="border-0" colSpan={5}>
						<div
							className="bg-success-lighten-2 label-borderless-success rounded-xs w-100"
							role="alert"
						>
							<div className="d-flex justify-content-between p-1">
								<div className="align-items-center borderless col-5 d-flex pr-0">
									<span className="alert-indicator"></span>

									<strong className="m-0 p-1">
										<ClayIcon
											className="clay-icon-next p-0"
											symbol="info-circle"
										/>

										<span className="font-weight-semi-bold p-1">
											Next Step:
										</span>
									</strong>

									<span className="m-0 p-1">
										Review estimation for {claimNumber}
									</span>
								</div>

								<div className="align-items-center border-0 col-2 d-flex justify-content-end px-0">
									<a
										className="m-0 p-1 view-detail-link"
										href="#"
									>
										View Detail
									</a>

									<ClayButtonWithIcon
										displayType={null}
										onClick={() => setVisible(false)}
										symbol="times"
									></ClayButtonWithIcon>
								</div>
							</div>
						</div>
					</ClayTable.Cell>
				</ClayTable.Row>
			)}
		</>
	);
};
export default Alert;
