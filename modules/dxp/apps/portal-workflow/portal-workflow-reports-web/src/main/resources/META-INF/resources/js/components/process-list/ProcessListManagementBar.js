import React from 'react';

export default class ProcessListManagementBar extends React.Component {
	render() {
		return (
			<div>
				<nav className="management-bar management-bar-light navbar navbar-expand-md">
					<div className="container">
						<ul className="navbar-nav">
							<li className="dropdown nav-item">
								<a
									aria-expanded="false"
									aria-haspopup="true"
									className="dropdown-toggle nav-link navbar-breakpoint-down-d-none"
									data-toggle="dropdown"
									href="#1"
									role="button"
								>
									<span className="navbar-text-truncate">
										{'Filter and Order2'}
									</span>
									<svg
										className="lexicon-icon lexicon-icon-caret-bottom"
										focusable="false"
										role="presentation"
									>
										<use href="/images/icons/icons.svg#caret-bottom" />
									</svg>
								</a>
								<a
									aria-expanded="false"
									aria-haspopup="true"
									className="nav-link nav-link-monospaced dropdown-toggle navbar-breakpoint-d-none"
									data-toggle="dropdown"
									href="#1"
									role="button"
								>
									<svg
										className="lexicon-icon lexicon-icon-filter"
										focusable="false"
										role="presentation"
									>
										<use href="/images/icons/icons.svg#filter" />
									</svg>
								</a>
								<div className="dropdown-menu" role="menu">
									<ul className="list-unstyled">
										<li>
											<a className="dropdown-item" href="#1" role="button">
												{'Filter Action 1'}
											</a>
										</li>
										<li>
											<a className="dropdown-item" href="#1" role="button">
												{'Filter Action 2'}
											</a>
										</li>
										<li>
											<a className="dropdown-item" href="#1" role="button">
												{'Filter Action 3'}
											</a>
										</li>
									</ul>
								</div>
							</li>
						</ul>
						<div className="navbar-form navbar-form-autofit navbar-overlay navbar-overlay-sm-down">
							<div className="container">
								<form role="search">
									<div className="input-group">
										<div className="input-group-item">
											<input
												className="form-control input-group-inset input-group-inset-after"
												placeholder="Search for..."
												type="text"
											/>
											<span className="input-group-inset-item input-group-inset-item-after">
												<button
													className="btn btn-unstyled navbar-breakpoint-d-none"
													type="button"
												>
													<svg
														className="lexicon-icon lexicon-icon-times"
														focusable="false"
														role="presentation"
													>
														<use href="/images/icons/icons.svg#times" />
													</svg>
												</button>
												<button
													className="btn btn-unstyled navbar-breakpoint-d-block"
													type="button"
												>
													<svg
														className="lexicon-icon lexicon-icon-search"
														focusable="false"
														role="presentation"
													>
														<use href="/images/icons/icons.svg#search" />
													</svg>
												</button>
											</span>
										</div>
									</div>
								</form>
							</div>
						</div>
					</div>
				</nav>
			</div>
		);
	}
}