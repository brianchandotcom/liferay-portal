import ClayIcon from '@clayui/icon';
import React from 'react';
import {toRounded} from 'shared/util/numbers';

interface ImprovementCellIProps
	extends React.TdHTMLAttributes<HTMLTableCellElement> {
	improvement: number;
}

const ImprovementCell: React.FC<ImprovementCellIProps> = ({
	improvement,
	...otherProps
}) => {
	const classname =
		improvement === 0 ? '' : improvement > 0 ? 'lift' : 'loss';
	const prefix =
		improvement > 0 ? (
			<ClayIcon className='icon-root' symbol='caret-top' />
		) : (
			<ClayIcon className='icon-root' symbol='caret-bottom' />
		);
	const sufix =
		improvement > 0
			? Liferay.Language.get('lift').toLowerCase()
			: Liferay.Language.get('loss').toLowerCase();

	return (
		<td {...otherProps}>
			<span className={classname}>
				{improvement !== 0 ? (
					<>
						{prefix} {`${toRounded(Math.abs(improvement), 2)}%`}{' '}
						{sufix}
					</>
				) : (
					'-'
				)}
			</span>
		</td>
	);
};

export default ImprovementCell;
