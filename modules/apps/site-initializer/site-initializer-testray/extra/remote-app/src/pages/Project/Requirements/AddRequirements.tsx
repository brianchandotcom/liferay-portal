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

import Input from '../../../components/Input';
import Container from '../../../components/Layout/Container';
import i18n from '../../../i18n';

const AddRequirements: React.FC = () => {
	return (
		<ClayLayout.Container>
			<Container>
				<ClayForm>
					<ClayLayout.Row justify="start">
						<ClayLayout.Col size={3} sm={12} xl={7}>
							<ClayForm.Group className="form-group-sm">
								<Input
									label={i18n.translate('first-name')}
									name="firstname"
									required
								/>

								<Input
									label={i18n.translate('last-name')}
									name="lastname"
									required
								/>

								<Input
									label={i18n.translate('email-address')}
									name="emailAddress"
									required
								/>

								<Input
									label={i18n.translate('screen-name')}
									name="screeName"
									required
								/>
							</ClayForm.Group>
						</ClayLayout.Col>
					</ClayLayout.Row>

					<hr />

					<ClayLayout.Row justify="start">
						<ClayLayout.Col size={3} sm={12} xl={3}>
							<h5 className="font-weight-normal">
								{i18n.translate('Description')}
							</h5>
						</ClayLayout.Col>

						<ClayLayout.Col size={3} sm={12} xl={3}>
							<ClayForm.Group className="form-group-sm">
								<br />

								<Input
									label=""
									name=""
									placeholder={i18n.translate('first-name')}
									type="textarea"
								/>
							</ClayForm.Group>
						</ClayLayout.Col>
					</ClayLayout.Row>

					<hr />

					<ClayLayout.Row justify="start">
						<ClayLayout.Col size={3} sm={12} xl={3}>
							<h5 className="font-weight-normal">
								{i18n.translate('change-password')}
							</h5>
						</ClayLayout.Col>

						<ClayLayout.Col size={3} sm={12} xl={3}>
							<ClayForm.Group className="form-group-sm">
								<ClayButton className="bg-neutral-2 borderless neutral text-neutral-7">
									{i18n.translate('change-password')}
								</ClayButton>
							</ClayForm.Group>
						</ClayLayout.Col>
					</ClayLayout.Row>

					<hr />
				</ClayForm>
			</Container>
		</ClayLayout.Container>
	);
};
export default AddRequirements;
