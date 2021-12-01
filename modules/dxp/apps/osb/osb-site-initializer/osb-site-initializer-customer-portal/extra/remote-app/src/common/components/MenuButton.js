import {useState} from 'react';

const MenuButton = () => {
	const [visibility, setVisibility] = useState('hidden');

	return (
		<div
			className="align-items-center d-flex dropdown-btn"
			onClick={() =>
				setVisibility((prev) =>
					prev === 'hidden' ? 'shown' : 'hidden'
				)
			}
		>
			<label>
				<input readOnly value="Value" />
			</label>

			<div className={`dropDownCard ${visibility}`}>
				<ul className="" id="lista">
					<li className="active">
						<div className="d-flex">
							<div>Project 1</div>
						</div>

						{/* <span className="alig-items-center d-flex">
									<img alt="" src="./src/checked.svg" />
								</span> */}
					</li>

					<li>
						<p className="d-flex">Project 2</p>
					</li>

					<li>
						<p className="d-flex">Project 3</p>
					</li>
				</ul>
			</div>
		</div>
	);
};

export default MenuButton;
