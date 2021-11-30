import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import ProgressBar from '../../../../../common/components/progress-bar';

const PreviewBody = ({
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

const PreviewDocument = ({
	file,
	onRemoveFile,
	showCloseButton = true,
	showName = true,
	type = 'image',
}) => (
	<div className="view-file-document view-file-margin-right">
		<div className="div-document" title={file.name}>
			<div className="content">
				{type === 'image' ? (
					<div className="image">
						<img alt={file.name} src={file.fileURL} />
					</div>
				) : (
					<ClayIcon className={file.icon} symbol={file.icon} />
				)}
			</div>

			<PreviewBody
				file={file}
				onRemoveFile={onRemoveFile}
				showCloseButton={showCloseButton}
				showName={showName}
			/>
		</div>
	</div>
);

const PreviewDocuments = ({files = [], onRemoveFile, type}) => (
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

						<PreviewBody
							file={file}
							onRemoveFile={onRemoveFile}
							showCloseButton={false}
						/>
					</div>
				);
			}

			return (
				<PreviewDocument
					file={file}
					key={index}
					onRemoveFile={onRemoveFile}
					type={type}
				/>
			);
		})}
	</div>
);

export default PreviewDocuments;
