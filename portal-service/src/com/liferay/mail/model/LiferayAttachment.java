package com.liferay.mail.model;

import java.io.File;

public class LiferayAttachment {

	public LiferayAttachment() {
	}

	public LiferayAttachment(File attachment, String attachmentFileName) {
		this.attachment = attachment;
		this.attachmentFileName = attachmentFileName;
	}
	
	public File getAttachment() {
		return attachment;
	}
	
	public void setAttachment(File attachment) {
		this.attachment = attachment;
	}
	
	public String getAttachmentFileName() {
		return attachmentFileName;
	}
	
	public void setAttachmentFileName(String attachmentFileName) {
		this.attachmentFileName = attachmentFileName;
	}

	private File attachment;
	private String attachmentFileName;
	
}
