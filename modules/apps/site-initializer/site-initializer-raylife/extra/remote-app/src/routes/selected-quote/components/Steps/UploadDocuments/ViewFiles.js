import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import ProgressBar from '~/common/components/progress-bar';
import ViewDocuments from './ViewDocuments';

const ViewBody = ({
	file,
	onRemoveFile,
	showCloseButton = true,
	showName = true,
}) => (
	<>
		{showName && (
			<span className="ellipsis" title={file.name}>
				{file.name}
			</span>
		)}

		{showCloseButton && (
			<div className="close-icon" onClick={() => onRemoveFile(file)}>
				<ClayIcon symbol="times" />
			</div>
		)}
	</>
);

const ViewFiles = ({files = [], onRemoveFile, type}) => {
	return (
		<div className="view-file">
			{files.map((file, index) => {
				if (file.progress < 100) {
					return (
						<div className="flex-column" title={file.name}>
							<div
								className={classNames('card', {
									spaced: index < 3,
								})}
							>
								<p>Uploading...</p>

								<ProgressBar
									height="4"
									progress={file.progress}
									width="144"
								/>
							</div>

							<ViewBody
								file={file}
								onRemoveFile={onRemoveFile}
								showCloseButton={false}
							/>
						</div>
					);
				}

				if (type === 'image') {
					<ViewDocuments
						file={file}
						onRemoveFile={onRemoveFile}
						type={type}
					/>;
				}

				return (
					<ViewDocuments
						file={file}
						key={index}
						onRemoveFile={onRemoveFile}
						type={type}
					/>
				);
			})}
		</div>
	);
};

export default ViewFiles;
