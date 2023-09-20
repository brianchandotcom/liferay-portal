import Label from 'shared/components/Label';
import Popover from 'shared/components/Popover';
import React, {useRef, useState} from 'react';
import ReactDOM from 'react-dom';
import {isEllipisActive} from 'shared/util/util';

interface IVariantTitleProps {
	labels: Array<{value: string; status: string}>;
	title: string;
}

const VariantTitle: React.FC<IVariantTitleProps> = ({labels = [], title}) => {
	const [showPopover, setShowPopover] = useState(false);
	const titleRef = useRef();

	const handleMouseOut = () => setShowPopover(false);
	const handleMouseOver = event => setShowPopover(isEllipisActive(event));

	return (
		<td className='table-cell-expanded'>
			<h5
				className='variant-title mb-1 text-truncate'
				onBlur={handleMouseOut}
				onFocus={handleMouseOver}
				onMouseOut={handleMouseOut}
				onMouseOver={handleMouseOver}
				ref={titleRef}
			>
				{title}
			</h5>

			{!!labels.length &&
				labels.map(({status, value}, index) => (
					<Label display={status} key={index}>
						{value}
					</Label>
				))}

			{ReactDOM.createPortal(
				<Popover
					alignElement={titleRef.current}
					title={title}
					visible={showPopover}
				/>,
				document.querySelector('body.dxp')
			)}
		</td>
	);
};

export default VariantTitle;
