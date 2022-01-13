import {Liferay} from '.';

export function getLiferaySiteName() {
	const {pathname} = new URL(Liferay.ThemeDisplay.getCanonicalURL());
	const pathSplit = pathname.split('/').filter(Boolean);

	return `${(pathSplit.length > 2
		? pathSplit.slice(0, pathSplit.length - 1)
		: pathSplit
	).join('/')}`;
}
