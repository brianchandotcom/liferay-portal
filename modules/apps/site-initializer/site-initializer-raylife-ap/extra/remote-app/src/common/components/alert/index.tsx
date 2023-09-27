/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
