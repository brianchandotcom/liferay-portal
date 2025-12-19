import ClayButton from '@clayui/button';
import ClayDropdown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import DownloadPDFReport from './DownloadPDFReport';
import Loading, {Align} from '../Loading';
import React from 'react';
import {CSVType, useMutationObserver} from './utils';
import {DownloadStaticCSVReport} from './DownloadStaticCSVReport';
import {useDataSource} from 'shared/hooks/useDataSource';

const reportType = {
	CSV: {
		Component: props => (
			<DownloadStaticCSVReport
				segmentId={props.segmentId}
				type={CSVType.Membership}
				{...props}
			/>
		),
		label: Liferay.Language.get('csv-file'),
		value: 'csv'
	},
	PDF: {
		Component: props => (
			<DownloadPDFReport showDateRange={false} {...props} />
		),
		label: Liferay.Language.get('pdf-file'),
		value: 'pdf'
	}
};

const DownloadReportDropdown = ({className, onClick, segmentId, title}) => {
	const {loadingCount} = useMutationObserver();
	const dataSourceStates = useDataSource();

	return (
		<>
			<ClayDropdown
				className={className}
				closeOnClick
				trigger={
					<ClayButton
						borderless
						displayType='secondary'
						onClick={onClick}
						size='sm'
					>
						<ClayIcon className='mr-2' symbol='download' />

						{Liferay.Language.get('download-reports')}

						<ClayIcon className='ml-2' symbol='caret-bottom' />
					</ClayButton>
				}
			>
				<ClayDropdown.ItemList>
					{Object.values(reportType).map(dropdownItem => (
						<dropdownItem.Component
							disabled={
								dataSourceStates.empty ||
								(dropdownItem.value === 'pdf' && !!loadingCount)
							}
							key={dropdownItem.value}
							segmentId={segmentId}
							title={title}
						>
							<ClayDropdown.Item>
								{dropdownItem.value === 'pdf' &&
									!!loadingCount && (
										<Loading align={Align.Left} />
									)}

								{dropdownItem.label}
							</ClayDropdown.Item>
						</dropdownItem.Component>
					))}
				</ClayDropdown.ItemList>
			</ClayDropdown>
		</>
	);
};

export {DownloadReportDropdown};
