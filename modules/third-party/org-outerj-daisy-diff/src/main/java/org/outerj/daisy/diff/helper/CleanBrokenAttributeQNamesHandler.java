/*
 * Copyright 2019 Atlassian Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.outerj.daisy.diff.helper;

import org.apache.xerces.util.XML11Char;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import javax.xml.XMLConstants;

class CleanBrokenAttributeQNamesHandler implements ContentHandler {
    private ContentHandler consumer;

    public CleanBrokenAttributeQNamesHandler(ContentHandler consumer) {
        this.consumer = consumer;
    }

    public void characters(char ch[], int start, int length)
        throws SAXException {
        consumer.characters(ch, start, length);
    }

    public void endDocument() throws SAXException {
        consumer.endDocument();
    }

    public void startDocument() throws SAXException {
        consumer.startDocument();
    }

    public void ignorableWhitespace(char ch[], int start, int length)
        throws SAXException {
        consumer.ignorableWhitespace(ch, start, length);
    }

    public void endPrefixMapping(String prefix) throws SAXException {
        consumer.endPrefixMapping(prefix);
    }

    public void skippedEntity(String name) throws SAXException {
        consumer.skippedEntity(name);
    }

    public void setDocumentLocator(Locator locator) {
        consumer.setDocumentLocator(locator);
    }

    public void processingInstruction(String target, String data)
        throws SAXException {
        consumer.processingInstruction(target, data);
    }

    public void startPrefixMapping(String prefix, String uri)
        throws SAXException {
        consumer.startPrefixMapping(prefix, uri);
    }

    public void endElement(String namespaceURI, String localName, String qName)
        throws SAXException {
        consumer.endElement(namespaceURI, localName, qName);
    }

    public void startElement(String namespaceURI, String localName,
                             String qName, Attributes atts) throws SAXException {
        consumer.startElement(namespaceURI, localName, qName, filterBrokenQNameAttributes(atts));
    }

    private static Attributes filterBrokenQNameAttributes(Attributes atts) {
        AttributesImpl filtered = new AttributesImpl();
        for (int i = 0, l = atts.getLength(); i < l; i++) {
            String localName = atts.getLocalName(i);
            if (XML11Char.isXML11ValidNCName(localName) && !localName.equals(XMLConstants.XMLNS_ATTRIBUTE)) {
                filtered.addAttribute(atts.getURI(i), localName, atts.getQName(i), atts.getType(i), atts.getValue(i));
            }
        }
        return filtered;
    }
}
/* @generated */
