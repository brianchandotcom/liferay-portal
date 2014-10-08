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

package com.liferay.portal.kernel.mail;

import com.liferay.mail.model.FileAttachment;

import java.io.File;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.InternetAddress;

/**
 * @author Brian Wing Shun Chan
 * @author Neil Griffin
 * @author Raymond Augé
 * @author Thiago Moreira
 */
public class MailMessage implements Serializable {

	public MailMessage() {
	}

	public MailMessage(
		InternetAddress from, InternetAddress to, String subject, String body,
		boolean htmlFormat) {

		_from = from;

		if (to != null) {
			_to = new InternetAddress[] {to};
		}
		else {
			_to = new InternetAddress[0];
		}

		_subject = subject;
		_body = body;
		_htmlFormat = htmlFormat;
	}

	public MailMessage(
		InternetAddress from, String subject, String body, boolean htmlFormat) {

		this(from, null, subject, body, htmlFormat);
	}

	public void addFileAttachment(File file) {
		addFileAttachment(file, null);
	}

	public void addFileAttachment(File file, String fileName) {
		if (file == null) {
			return;
		}

		FileAttachment fileAttachment = new FileAttachment(file, fileName);

		_fileAttachments.add(fileAttachment);
	}

	public InternetAddress[] getBCC() {
		return _bcc;
	}

	public String getBody() {
		return _body;
	}

	public InternetAddress[] getBulkAddresses() {
		return _bulkAddresses;
	}

	public InternetAddress[] getCC() {
		return _cc;
	}

	public List<FileAttachment> getFileAttachments() {
		return _fileAttachments;
	}

	public InternetAddress getFrom() {
		return _from;
	}

	public boolean getHTMLFormat() {
		return _htmlFormat;
	}

	public String getInReplyTo() {
		return _inReplyTo;
	}

	public String getMessageId() {
		return _messageId;
	}

	public InternetAddress[] getReplyTo() {
		return _replyTo;
	}

	public SMTPAccount getSMTPAccount() {
		return _smtpAccount;
	}

	public String getSubject() {
		return _subject;
	}

	public InternetAddress[] getTo() {
		return _to;
	}

	public boolean isHTMLFormat() {
		return _htmlFormat;
	}

	public void setBCC(InternetAddress bcc) {
		_bcc = new InternetAddress[] {bcc};
	}

	public void setBCC(InternetAddress[] bcc) {
		_bcc = bcc;
	}

	public void setBody(String body) {
		_body = body;
	}

	public void setBulkAddresses(InternetAddress[] bulkAddresses) {
		_bulkAddresses = bulkAddresses;
	}

	public void setCC(InternetAddress cc) {
		_cc = new InternetAddress[] {cc};
	}

	public void setCC(InternetAddress[] cc) {
		_cc = cc;
	}

	public void setFrom(InternetAddress from) {
		_from = from;
	}

	public void setHTMLFormat(boolean htmlFormat) {
		_htmlFormat = htmlFormat;
	}

	public void setInReplyTo(String inReplyTo) {
		_inReplyTo = inReplyTo;
	}

	public void setMessageId(String messageId) {
		_messageId = messageId;
	}

	public void setReplyTo(InternetAddress[] replyTo) {
		_replyTo = replyTo;
	}

	public void setSMTPAccount(SMTPAccount account) {
		_smtpAccount = account;
	}

	public void setSubject(String subject) {
		_subject = subject;
	}

	public void setTo(InternetAddress to) {
		_to = new InternetAddress[] {to};
	}

	public void setTo(InternetAddress[] to) {
		_to = to;
	}

	private InternetAddress[] _bcc;
	private String _body;
	private InternetAddress[] _bulkAddresses;
	private InternetAddress[] _cc;
	private final List<FileAttachment> _fileAttachments =
		new ArrayList<FileAttachment>();
	private InternetAddress _from;
	private boolean _htmlFormat;
	private String _inReplyTo;
	private String _messageId;
	private InternetAddress[] _replyTo;
	private SMTPAccount _smtpAccount;
	private String _subject;
	private InternetAddress[] _to;

}