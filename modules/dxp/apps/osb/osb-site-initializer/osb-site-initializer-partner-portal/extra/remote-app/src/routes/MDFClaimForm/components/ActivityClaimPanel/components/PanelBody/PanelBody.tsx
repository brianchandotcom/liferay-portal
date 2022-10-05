/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayPanel from '@clayui/panel';
import {setElementFullHeight} from '@clayui/shared';
import classNames from 'classnames';
import {CSSTransition} from 'react-transition-group';

interface IProps {
	children?: React.ReactNode;
	expanded: boolean;
}

const PanelBody = ({children, expanded}: IProps) => (
	<CSSTransition
		className={classNames('panel-collapse', {
			collapse: !expanded,
		})}
		classNames={{
			enter: 'collapsing',
			enterActive: `show`,
			enterDone: 'show',
			exit: `show`,
			exitActive: 'collapsing',
		}}
		in={expanded}
		onEnter={(element: HTMLElement) =>
			element.setAttribute('style', `height: 0px`)
		}
		onEntering={(element: HTMLElement) => setElementFullHeight(element)}
		onExit={(element) => setElementFullHeight(element)}
		onExiting={(element) => element.setAttribute('style', `height: 0px`)}
		role="tabpanel"
		timeout={250}
	>
		<div>
			<ClayPanel.Body className="mx-2 pb-0 pt-4 px-5">
				{children}
			</ClayPanel.Body>
		</div>
	</CSSTransition>
);

export default PanelBody;
