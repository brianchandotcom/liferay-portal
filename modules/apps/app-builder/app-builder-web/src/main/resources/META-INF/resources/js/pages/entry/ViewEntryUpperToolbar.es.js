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
import ClayButtonGroup from '@clayui/button/lib/Group';
import {ClayTooltipProvider} from '@clayui/tooltip';
import React from 'react';
import {withRouter} from 'react-router-dom';

import UpperToolbar from '../../components/upper-toolbar/UpperToolbar.es';
import Lang from '../../utils/lang.es';

export default withRouter(({onDelete, onEdit, onNext, onPrev, page, total}) => {
	return (
		<UpperToolbar>
			<UpperToolbar.Item className="text-left" expand={true}>
				<label>
					{Lang.sub(Liferay.Language.get('x-of-x-entries'), [
						page,
						total
					])}
				</label>
			</UpperToolbar.Item>

			<UpperToolbar.Group>
				<ClayTooltipProvider>
					<ClayButtonGroup>
						<ClayButtonWithIcon
							data-tooltip-align="bottom"
							data-tooltip-delay="200"
							disabled={page === 1}
							displayType="secondary"
							onClick={onPrev}
							small
							symbol="angle-left"
							title={Liferay.Language.get('previous-entry')}
						/>

						<ClayButtonWithIcon
							data-tooltip-align="bottom"
							data-tooltip-delay="200"
							disabled={page === total}
							displayType="secondary"
							onClick={onNext}
							small
							symbol="angle-right"
							title={Liferay.Language.get('next-entry')}
						/>
					</ClayButtonGroup>
				</ClayTooltipProvider>
			</UpperToolbar.Group>

			<UpperToolbar.Group>
				<UpperToolbar.Button displayType="secondary" onClick={onDelete}>
					{Liferay.Language.get('delete')}
				</UpperToolbar.Button>

				<UpperToolbar.Button onClick={onEdit}>
					{Liferay.Language.get('edit')}
				</UpperToolbar.Button>
			</UpperToolbar.Group>
		</UpperToolbar>
	);
});
