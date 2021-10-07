import ClayIcon from '@clayui/icon';

const ViewBody = ({file, onRemoveFile, showName = true}) => (
	<>
		{showName && <span>{file.name}</span>}

		<div className="close-icon" onClick={() => onRemoveFile(file)}>
			<ClayIcon symbol="times" />
		</div>
	</>
);

const ViewFiles = ({files = [], onRemoveFile, type}) => {
	return (
		<div className="view-file">
			{files.map((file, index) =>
				type === 'image' ? (
					<div className="view-file-image" key={index}>
						<div className="div-image" title={file.name}>
							<img alt={file.name} src={file.fileURL} />

							<span>{file.name}</span>
						</div>

						<ViewBody
							file={file}
							onRemoveFile={onRemoveFile}
							showName={false}
						/>
					</div>
				) : (
					<div className="view-file-document" key={index}>
						<div className="div-document" title={file.name}>
							<div className="content">
								<ClayIcon
									class={file.icon}
									symbol={file.icon}
								/>
							</div>

							<ViewBody file={file} onRemoveFile={onRemoveFile} />
						</div>
					</div>
				)
			)}
		</div>
	);
};

export default ViewFiles;
