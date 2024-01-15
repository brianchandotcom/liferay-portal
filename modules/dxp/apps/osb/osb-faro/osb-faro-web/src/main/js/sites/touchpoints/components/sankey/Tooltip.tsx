import React from 'react';
import {sub} from 'shared/util/lang';
import {toLocale} from 'shared/util/numbers';

export const Tooltip: React.FC<any> = ({payload}) => {
	if (!payload.length) return null;

	const data = payload[0].payload?.payload;

	let description = Liferay.Language.get('page-views');
	let name = payload[0].name;

	if (!data?.main) {
		if (data?.type === 'previous' || data?.target?.main) {
			description = sub(Liferay.Language.get('page-views-x'), [
				Liferay.Language.get('referral')
			]);
			name = data?.source?.name ?? payload[0].name;
		} else {
			description = sub(Liferay.Language.get('page-views-x'), [
				Liferay.Language.get('exit-pages')
			]);
			name = data?.target?.name ?? payload[0].name;
		}
	}

	return (
		<div
			className='clay-popover-top fade popover position-relative show'
			style={{minWidth: 240}}
		>
			<div className='popover-header'>{description}</div>
			<div className='popover-body'>
				<div className='d-flex justify-content-between'>
					<div
						className='mr-2'
						style={{maxWidth: 'calc(100% - 50px)'}}
					>
						{name}
					</div>

					<div>{toLocale(payload[0].value)}</div>
				</div>
			</div>
		</div>
	);
};
