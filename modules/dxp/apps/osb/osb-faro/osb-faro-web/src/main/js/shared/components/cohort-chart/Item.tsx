import Popover from 'shared/components/Popover';
import React, {useRef, useState} from 'react';
import ReactDOM from 'react-dom';
import {CohortHeatMapType} from './index';
import {sub} from 'shared/util/lang';

const Item: React.FC<CohortHeatMapType> = ({
	colorHex,
	date,
	dateLabelFn,
	periodLabel,
	retention,
	value
}) => {
	const _ref = useRef();

	const [visible, setVisible] = useState(false);

	const handleMouseOut = () => setVisible(false);
	const handleMouseOver = () => setVisible(true);

	const content = (
		<span className='cohort-item-popover d-flex justify-content-between'>
			<span className='period'>{periodLabel}</span>

			<span className='visitors'>
				{sub(Liferay.Language.get('x-visitors'), [
					value.toLocaleString()
				])}
			</span>
		</span>
	);

	return (
		<td
			className='cohort-item-root table-column-text-center'
			onBlur={handleMouseOver}
			onFocus={handleMouseOut}
			onMouseOut={handleMouseOut}
			onMouseOver={handleMouseOver}
			ref={_ref}
			style={{background: colorHex}}
		>
			<span className='retention'>{`${retention.toFixed(2)}%`}</span>

			{ReactDOM.createPortal(
				<Popover
					alignElement={_ref.current}
					content={content}
					title={sub(Liferay.Language.get('x-cohort'), [
						dateLabelFn(date, true)
					])}
					visible={visible}
				/>,
				document.querySelector('body.dxp')
			)}
		</td>
	);
};

export default Item;
