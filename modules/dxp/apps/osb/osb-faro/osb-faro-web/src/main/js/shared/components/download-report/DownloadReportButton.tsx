import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import Loading, {Align} from 'shared/components/Loading';
import React, {useEffect, useState} from 'react';

interface IDownloadReportButton {
	disabled: boolean;
	loading?: boolean;
	onClick: () => void;
}

export const DownloadReportButton: React.FC<IDownloadReportButton> = ({
	disabled,
	loading = false,
	onClick
}) => {
	const [loadingCount, setLoadingCount] = useState(0);

	useEffect(() => {
		const observer = new MutationObserver(() => {
			const loadingElement = document.querySelectorAll(
				'.page-container .loading-animation'
			);

			setLoadingCount(loadingElement.length);
		});

		observer.observe(document.body, {
			attributes: true,
			characterData: true,
			childList: true,
			subtree: true
		});

		return () => observer.disconnect();
	}, []);

	return (
		<ClayButton
			borderless
			disabled={disabled || loading || loadingCount > 0}
			displayType='secondary'
			onClick={onClick}
			size='sm'
		>
			<ClayIcon className='mr-2' symbol='download' />

			{Liferay.Language.get('download-reports')}

			{(loading || loadingCount > 0) && <Loading align={Align.Right} />}
		</ClayButton>
	);
};
