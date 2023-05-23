/*******************************************************************************
 * Copyright (c) Jan. 26, 2019 Liferay, Inc.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Liferay, Inc. - initial API and implementation and/or initial
 *                    documentation
 ******************************************************************************/

package org.eclipse.equinox.http.servlet.internal.util;

/**
 * @author Raymond Augé
 */
public enum HttpStatus {

    UNKNOWN_STATUS(-1, "Unknown Status", ""), //$NON-NLS-1$ //$NON-NLS-2$

    CONTINUE(100, "Continue", "RFC7231"), //$NON-NLS-1$ //$NON-NLS-2$

    SWITCHING_PROTOCOLS(101, "Switching Protocols", "RFC7231"), //$NON-NLS-1$ //$NON-NLS-2$

    PROCESSING(102, "Processing", "RFC2518"), //$NON-NLS-1$ //$NON-NLS-2$

    EARLY_HINTS(103, "Early Hints", "RFC8297"), //$NON-NLS-1$ //$NON-NLS-2$

    OK(200, "OK", "RFC7231"), //$NON-NLS-1$ //$NON-NLS-2$

    CREATED(201, "Created", "RFC7231"), //$NON-NLS-1$ //$NON-NLS-2$

    ACCEPTED(202, "Accepted", "RFC7231"), //$NON-NLS-1$ //$NON-NLS-2$

    NON_AUTHORITATIVE_INFORMATION(203, "Non Authoritative Information", "RFC7231"), //$NON-NLS-1$ //$NON-NLS-2$

    NO_CONTENT(204, "No Content", "RFC7231"), //$NON-NLS-1$ //$NON-NLS-2$

    RESET_CONTENT(205, "Reset Content", "RFC7231"), //$NON-NLS-1$ //$NON-NLS-2$

    PARTIAL_CONTENT(206, "Partial Content", "RFC7233"), //$NON-NLS-1$ //$NON-NLS-2$
    /** )*/
    MULTI_STATUS(207, "Partial Update OK", "RFC4918"), //$NON-NLS-1$ //$NON-NLS-2$

    ALREADY_REPORTED(208, "Already Reported", "RFC5842"), //$NON-NLS-1$ //$NON-NLS-2$

    IM_USED(226, "IM Used", "RFC3229"), //$NON-NLS-1$ //$NON-NLS-2$

    MULTIPLE_CHOICES(300, "Multiple Choices", "RFC7231"), //$NON-NLS-1$ //$NON-NLS-2$

    MOVED_PERMANENTLY(301, "Moved Permanently", "RFC7231"), //$NON-NLS-1$ //$NON-NLS-2$

    MOVED_TEMPORARILY(302, "Found", "RFC7231"), //$NON-NLS-1$ //$NON-NLS-2$

    SEE_OTHER(303, "See Other", "RFC7231"), //$NON-NLS-1$ //$NON-NLS-2$

    NOT_MODIFIED(304, "Not Modified", "RFC7232"), //$NON-NLS-1$ //$NON-NLS-2$

    USE_PROXY(305, "Use Proxy", "RFC7231"), //$NON-NLS-1$ //$NON-NLS-2$

    TEMPORARY_REDIRECT(307, "Temporary Redirect", "RFC7231"), //$NON-NLS-1$ //$NON-NLS-2$

    PERMANENT_REDIRECT(308, "Permanent Redirect", "RFC7538"), //$NON-NLS-1$ //$NON-NLS-2$

    BAD_REQUEST(400, "Bad Request", "RFC7231"), //$NON-NLS-1$ //$NON-NLS-2$

    UNAUTHORIZED(401, "Unauthorized", "RFC7235"), //$NON-NLS-1$ //$NON-NLS-2$

    PAYMENT_REQUIRED(402, "Payment Required", "RFC7231"), //$NON-NLS-1$ //$NON-NLS-2$

    FORBIDDEN(403, "Forbidden", "RFC7231"), //$NON-NLS-1$ //$NON-NLS-2$

    NOT_FOUND(404, "Not Found", "RFC7231"), //$NON-NLS-1$ //$NON-NLS-2$

    METHOD_NOT_ALLOWED(405, "Method Not Allowed", "RFC7231"), //$NON-NLS-1$ //$NON-NLS-2$

    NOT_ACCEPTABLE(406, "Not Acceptable", "RFC7231"), //$NON-NLS-1$ //$NON-NLS-2$

    PROXY_AUTHENTICATION_REQUIRED(407, "Proxy Authentication Required", "RFC7235"), //$NON-NLS-1$ //$NON-NLS-2$

    REQUEST_TIMEOUT(408, "Request Timeout", "RFC7231"), //$NON-NLS-1$ //$NON-NLS-2$

    CONFLICT(409, "Conflict", "RFC7231"), //$NON-NLS-1$ //$NON-NLS-2$

    GONE(410, "Gone", "RFC7231"), //$NON-NLS-1$ //$NON-NLS-2$

    LENGTH_REQUIRED(411, "Length Required", "RFC7231"), //$NON-NLS-1$ //$NON-NLS-2$

    PRECONDITION_FAILED(412, "Precondition Failed", "RFC7232"), //$NON-NLS-1$ //$NON-NLS-2$

    PAYLOAD_TOO_LARGE(413, "Payload Too Large", "RFC7231"), //$NON-NLS-1$ //$NON-NLS-2$

    URI_TOO_LONG(414, "URI Too Long", "RFC7231"), //$NON-NLS-1$ //$NON-NLS-2$

    UNSUPPORTED_MEDIA_TYPE(415, "Unsupported Media Type", "RFC7231"), //$NON-NLS-1$ //$NON-NLS-2$

    RANGE_NOT_SATISFIABLE(416, "Range Not Satisfiable", "RFC7233"), //$NON-NLS-1$ //$NON-NLS-2$

    EXPECTATION_FAILED(417, "Expectation Failed", "RFC7231"), //$NON-NLS-1$ //$NON-NLS-2$

    MISDIRECTED_REQUEST(421, "Misdirected Request", "RFC7540"), //$NON-NLS-1$ //$NON-NLS-2$

    UNPROCESSABLE_ENTITY(422, "Unprocessable Entity", "RFC4918"), //$NON-NLS-1$ //$NON-NLS-2$

    LOCKED(423, "Locked", "RFC4918"), //$NON-NLS-1$ //$NON-NLS-2$

    FAILED_DEPENDENCY(424, "Failed Dependency", "RFC4918"), //$NON-NLS-1$ //$NON-NLS-2$

    PRECONDITION_REQUIRED(428, "Precondition Required", "RFC6585"), //$NON-NLS-1$ //$NON-NLS-2$

    TOO_MANY_REQUESTS(429, "Too Many Requests", "RFC6585"), //$NON-NLS-1$ //$NON-NLS-2$

    REQUEST_HEADER_FIELDS_TOO_LARGE(431, "Request Header Fields Too Large", "RFC6585"), //$NON-NLS-1$ //$NON-NLS-2$

    UNAVAILABLE_FOR_LEGAL_REASONS(451, "Unavailable For Legal Reasons", "RFC7725"), //$NON-NLS-1$ //$NON-NLS-2$

    INTERNAL_SERVER_ERROR(500, "Internal Server Error", "RFC7231"), //$NON-NLS-1$ //$NON-NLS-2$

    NOT_IMPLEMENTED(501, "Not Implemented", "RFC7231"), //$NON-NLS-1$ //$NON-NLS-2$

    BAD_GATEWAY(502, "Bad Gateway", "RFC7231"), //$NON-NLS-1$ //$NON-NLS-2$

    SERVICE_UNAVAILABLE(503, "Service Unavailable", "RFC7231"), //$NON-NLS-1$ //$NON-NLS-2$

    GATEWAY_TIMEOUT(504, "Gateway Timeout", "RFC7231"), //$NON-NLS-1$ //$NON-NLS-2$

    HTTP_VERSION_NOT_SUPPORTED(505, "HTTP Version Not Supported", "RFC7231"), //$NON-NLS-1$ //$NON-NLS-2$

    VARIANT_ALSO_NEGOTIATES(506, "Variant Also Negotiates", "RFC2295"), //$NON-NLS-1$ //$NON-NLS-2$

    INSUFFICIENT_STORAGE(507, "Insufficient Storage", "RFC4918"), //$NON-NLS-1$ //$NON-NLS-2$

    LOOP_DETECTED(508, "Loop Detected", "RFC5842"), //$NON-NLS-1$ //$NON-NLS-2$

    NOT_EXTENDED(510, "Not Extended", "RFC2774"), //$NON-NLS-1$ //$NON-NLS-2$

    NETWORK_AUTHENTICATION_REQUIRED(511, "Network Authentication Required", "RFC6585"); //$NON-NLS-1$ //$NON-NLS-2$

    private HttpStatus(int value, String description, String reference) {
        this.value = value;
        this.description = description;
        this.reference = reference;
    }

    public String description() {
        return description;
    }

    public String reference() {
        return reference;
    }

    public int value() {
        return value;
    }

    private final int value;
    private final String description;
    private final String reference;

    public static HttpStatus of(int value) {
        for (HttpStatus v : HttpStatus.values()) {
            if (v.value == value) {
                return v;
            }
        }
        return UNKNOWN_STATUS;
    }
}
