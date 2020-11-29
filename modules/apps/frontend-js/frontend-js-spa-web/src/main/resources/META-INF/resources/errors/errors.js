/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

/**
 * Holds value error messages.
 * @const
 */
class errors {}

/**
 * Invalid status error message.
 * @type {string}
 * @static
 */
errors.INVALID_STATUS = 'Invalid status code';

/**
 * Request error message.
 * @type {string}
 * @static
 */
errors.REQUEST_ERROR = 'Request error';

/**
 * Request timeout error message.
 * @type {string}
 * @static
 */
errors.REQUEST_TIMEOUT = 'Request timeout';

/**
 * Request is blocked by CORS issue message.
 * @type {string}
 * @static
 */
errors.REQUEST_PREMATURE_TERMINATION = 'Request terminated prematurely';

export default errors;
