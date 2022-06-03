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
import ClayForm from '@clayui/form';
import ClayLayout from '@clayui/layout';
import {useNavigate} from 'react-router-dom';

import Input from '../../../components/Form/Input/index';
import Container from '../../../components/Layout/Container';
import i18n from '../../../i18n';

const ChangeUserPassword: React.FC = () => {
	const navigate = useNavigate();

	return (
		<ClayLayout.Container>
			<Container>
				<ClayForm>
					<ClayLayout.Row justify="start">
						<ClayLayout.Col size={3} sm={12} xl={3}>
							<h5 className="font-weight-normal">
								{i18n.translate('change-password')}
							</h5>
						</ClayLayout.Col>

						<ClayLayout.Col size={3} sm={12} xl={7}>
							<ClayForm.Group className="form-group-sm">
								<Input
									label={i18n.translate('new-password')}
									name="newpassword"
									type="password"
								/>

								<Input
									label={i18n.translate('new-password')}
									name="newpassword"
									type="password"
								/>
							</ClayForm.Group>

							<ClayLayout.Row>
								<ClayLayout.Col>
									<ClayButton.Group
										className="form-group-sm mt-2"
										key={3}
										spaced
									>
										<ClayButton
											className="bg-primary-2 borderless mr-2 primary text-primary-7"
											displayType="primary"
										>
											{i18n.translate('save')}
										</ClayButton>

										<ClayButton
											className="bg-neutral-2 borderless neutral text-neutral-7"
											displayType="secondary"
											onClick={() => navigate(-1)}
										>
											{i18n.translate('Cancel')}
										</ClayButton>
									</ClayButton.Group>
								</ClayLayout.Col>
							</ClayLayout.Row>
						</ClayLayout.Col>
					</ClayLayout.Row>
				</ClayForm>
			</Container>
		</ClayLayout.Container>
	);
};
export default ChangeUserPassword;
