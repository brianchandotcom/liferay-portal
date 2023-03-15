import getCN from 'classnames';
import React from 'react';
import Row from './Row';

const SubHeader: React.FC<React.HTMLAttributes<HTMLDivElement>> = ({
	children,
	className
}) => (
	<div className={getCN('sub-header-root', className)}>
		<Row className='header-container'>{children}</Row>
	</div>
);

export default SubHeader;
