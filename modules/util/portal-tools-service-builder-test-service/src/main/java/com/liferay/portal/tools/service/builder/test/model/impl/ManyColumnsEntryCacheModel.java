/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.tools.service.builder.test.model.ManyColumnsEntry;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing ManyColumnsEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ManyColumnsEntryCacheModel
	implements CacheModel<ManyColumnsEntry>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ManyColumnsEntryCacheModel)) {
			return false;
		}

		ManyColumnsEntryCacheModel manyColumnsEntryCacheModel =
			(ManyColumnsEntryCacheModel)object;

		if (manyColumnsEntryId ==
				manyColumnsEntryCacheModel.manyColumnsEntryId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, manyColumnsEntryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(131);

		sb.append("{manyColumnsEntryId=");
		sb.append(manyColumnsEntryId);
		sb.append(", column1=");
		sb.append(column1);
		sb.append(", column2=");
		sb.append(column2);
		sb.append(", column3=");
		sb.append(column3);
		sb.append(", column4=");
		sb.append(column4);
		sb.append(", column5=");
		sb.append(column5);
		sb.append(", column6=");
		sb.append(column6);
		sb.append(", column7=");
		sb.append(column7);
		sb.append(", column8=");
		sb.append(column8);
		sb.append(", column9=");
		sb.append(column9);
		sb.append(", column10=");
		sb.append(column10);
		sb.append(", column11=");
		sb.append(column11);
		sb.append(", column12=");
		sb.append(column12);
		sb.append(", column13=");
		sb.append(column13);
		sb.append(", column14=");
		sb.append(column14);
		sb.append(", column15=");
		sb.append(column15);
		sb.append(", column16=");
		sb.append(column16);
		sb.append(", column17=");
		sb.append(column17);
		sb.append(", column18=");
		sb.append(column18);
		sb.append(", column19=");
		sb.append(column19);
		sb.append(", column20=");
		sb.append(column20);
		sb.append(", column21=");
		sb.append(column21);
		sb.append(", column22=");
		sb.append(column22);
		sb.append(", column23=");
		sb.append(column23);
		sb.append(", column24=");
		sb.append(column24);
		sb.append(", column25=");
		sb.append(column25);
		sb.append(", column26=");
		sb.append(column26);
		sb.append(", column27=");
		sb.append(column27);
		sb.append(", column28=");
		sb.append(column28);
		sb.append(", column29=");
		sb.append(column29);
		sb.append(", column30=");
		sb.append(column30);
		sb.append(", column31=");
		sb.append(column31);
		sb.append(", column32=");
		sb.append(column32);
		sb.append(", column33=");
		sb.append(column33);
		sb.append(", column34=");
		sb.append(column34);
		sb.append(", column35=");
		sb.append(column35);
		sb.append(", column36=");
		sb.append(column36);
		sb.append(", column37=");
		sb.append(column37);
		sb.append(", column38=");
		sb.append(column38);
		sb.append(", column39=");
		sb.append(column39);
		sb.append(", column40=");
		sb.append(column40);
		sb.append(", column41=");
		sb.append(column41);
		sb.append(", column42=");
		sb.append(column42);
		sb.append(", column43=");
		sb.append(column43);
		sb.append(", column44=");
		sb.append(column44);
		sb.append(", column45=");
		sb.append(column45);
		sb.append(", column46=");
		sb.append(column46);
		sb.append(", column47=");
		sb.append(column47);
		sb.append(", column48=");
		sb.append(column48);
		sb.append(", column49=");
		sb.append(column49);
		sb.append(", column50=");
		sb.append(column50);
		sb.append(", column51=");
		sb.append(column51);
		sb.append(", column52=");
		sb.append(column52);
		sb.append(", column53=");
		sb.append(column53);
		sb.append(", column54=");
		sb.append(column54);
		sb.append(", column55=");
		sb.append(column55);
		sb.append(", column56=");
		sb.append(column56);
		sb.append(", column57=");
		sb.append(column57);
		sb.append(", column58=");
		sb.append(column58);
		sb.append(", column59=");
		sb.append(column59);
		sb.append(", column60=");
		sb.append(column60);
		sb.append(", column61=");
		sb.append(column61);
		sb.append(", column62=");
		sb.append(column62);
		sb.append(", column63=");
		sb.append(column63);
		sb.append(", column64=");
		sb.append(column64);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ManyColumnsEntry toEntityModel() {
		ManyColumnsEntryImpl manyColumnsEntryImpl = new ManyColumnsEntryImpl();

		manyColumnsEntryImpl.setManyColumnsEntryId(manyColumnsEntryId);
		manyColumnsEntryImpl.setColumn1(column1);
		manyColumnsEntryImpl.setColumn2(column2);
		manyColumnsEntryImpl.setColumn3(column3);
		manyColumnsEntryImpl.setColumn4(column4);
		manyColumnsEntryImpl.setColumn5(column5);
		manyColumnsEntryImpl.setColumn6(column6);
		manyColumnsEntryImpl.setColumn7(column7);
		manyColumnsEntryImpl.setColumn8(column8);
		manyColumnsEntryImpl.setColumn9(column9);
		manyColumnsEntryImpl.setColumn10(column10);
		manyColumnsEntryImpl.setColumn11(column11);
		manyColumnsEntryImpl.setColumn12(column12);
		manyColumnsEntryImpl.setColumn13(column13);
		manyColumnsEntryImpl.setColumn14(column14);
		manyColumnsEntryImpl.setColumn15(column15);
		manyColumnsEntryImpl.setColumn16(column16);
		manyColumnsEntryImpl.setColumn17(column17);
		manyColumnsEntryImpl.setColumn18(column18);
		manyColumnsEntryImpl.setColumn19(column19);
		manyColumnsEntryImpl.setColumn20(column20);
		manyColumnsEntryImpl.setColumn21(column21);
		manyColumnsEntryImpl.setColumn22(column22);
		manyColumnsEntryImpl.setColumn23(column23);
		manyColumnsEntryImpl.setColumn24(column24);
		manyColumnsEntryImpl.setColumn25(column25);
		manyColumnsEntryImpl.setColumn26(column26);
		manyColumnsEntryImpl.setColumn27(column27);
		manyColumnsEntryImpl.setColumn28(column28);
		manyColumnsEntryImpl.setColumn29(column29);
		manyColumnsEntryImpl.setColumn30(column30);
		manyColumnsEntryImpl.setColumn31(column31);
		manyColumnsEntryImpl.setColumn32(column32);
		manyColumnsEntryImpl.setColumn33(column33);
		manyColumnsEntryImpl.setColumn34(column34);
		manyColumnsEntryImpl.setColumn35(column35);
		manyColumnsEntryImpl.setColumn36(column36);
		manyColumnsEntryImpl.setColumn37(column37);
		manyColumnsEntryImpl.setColumn38(column38);
		manyColumnsEntryImpl.setColumn39(column39);
		manyColumnsEntryImpl.setColumn40(column40);
		manyColumnsEntryImpl.setColumn41(column41);
		manyColumnsEntryImpl.setColumn42(column42);
		manyColumnsEntryImpl.setColumn43(column43);
		manyColumnsEntryImpl.setColumn44(column44);
		manyColumnsEntryImpl.setColumn45(column45);
		manyColumnsEntryImpl.setColumn46(column46);
		manyColumnsEntryImpl.setColumn47(column47);
		manyColumnsEntryImpl.setColumn48(column48);
		manyColumnsEntryImpl.setColumn49(column49);
		manyColumnsEntryImpl.setColumn50(column50);
		manyColumnsEntryImpl.setColumn51(column51);
		manyColumnsEntryImpl.setColumn52(column52);
		manyColumnsEntryImpl.setColumn53(column53);
		manyColumnsEntryImpl.setColumn54(column54);
		manyColumnsEntryImpl.setColumn55(column55);
		manyColumnsEntryImpl.setColumn56(column56);
		manyColumnsEntryImpl.setColumn57(column57);
		manyColumnsEntryImpl.setColumn58(column58);
		manyColumnsEntryImpl.setColumn59(column59);
		manyColumnsEntryImpl.setColumn60(column60);
		manyColumnsEntryImpl.setColumn61(column61);
		manyColumnsEntryImpl.setColumn62(column62);
		manyColumnsEntryImpl.setColumn63(column63);
		manyColumnsEntryImpl.setColumn64(column64);

		manyColumnsEntryImpl.resetOriginalValues();

		return manyColumnsEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		manyColumnsEntryId = objectInput.readLong();

		column1 = objectInput.readInt();

		column2 = objectInput.readInt();

		column3 = objectInput.readInt();

		column4 = objectInput.readInt();

		column5 = objectInput.readInt();

		column6 = objectInput.readInt();

		column7 = objectInput.readInt();

		column8 = objectInput.readInt();

		column9 = objectInput.readInt();

		column10 = objectInput.readInt();

		column11 = objectInput.readInt();

		column12 = objectInput.readInt();

		column13 = objectInput.readInt();

		column14 = objectInput.readInt();

		column15 = objectInput.readInt();

		column16 = objectInput.readInt();

		column17 = objectInput.readInt();

		column18 = objectInput.readInt();

		column19 = objectInput.readInt();

		column20 = objectInput.readInt();

		column21 = objectInput.readInt();

		column22 = objectInput.readInt();

		column23 = objectInput.readInt();

		column24 = objectInput.readInt();

		column25 = objectInput.readInt();

		column26 = objectInput.readInt();

		column27 = objectInput.readInt();

		column28 = objectInput.readInt();

		column29 = objectInput.readInt();

		column30 = objectInput.readInt();

		column31 = objectInput.readInt();

		column32 = objectInput.readInt();

		column33 = objectInput.readInt();

		column34 = objectInput.readInt();

		column35 = objectInput.readInt();

		column36 = objectInput.readInt();

		column37 = objectInput.readInt();

		column38 = objectInput.readInt();

		column39 = objectInput.readInt();

		column40 = objectInput.readInt();

		column41 = objectInput.readInt();

		column42 = objectInput.readInt();

		column43 = objectInput.readInt();

		column44 = objectInput.readInt();

		column45 = objectInput.readInt();

		column46 = objectInput.readInt();

		column47 = objectInput.readInt();

		column48 = objectInput.readInt();

		column49 = objectInput.readInt();

		column50 = objectInput.readInt();

		column51 = objectInput.readInt();

		column52 = objectInput.readInt();

		column53 = objectInput.readInt();

		column54 = objectInput.readInt();

		column55 = objectInput.readInt();

		column56 = objectInput.readInt();

		column57 = objectInput.readInt();

		column58 = objectInput.readInt();

		column59 = objectInput.readInt();

		column60 = objectInput.readInt();

		column61 = objectInput.readInt();

		column62 = objectInput.readInt();

		column63 = objectInput.readInt();

		column64 = objectInput.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(manyColumnsEntryId);

		objectOutput.writeInt(column1);

		objectOutput.writeInt(column2);

		objectOutput.writeInt(column3);

		objectOutput.writeInt(column4);

		objectOutput.writeInt(column5);

		objectOutput.writeInt(column6);

		objectOutput.writeInt(column7);

		objectOutput.writeInt(column8);

		objectOutput.writeInt(column9);

		objectOutput.writeInt(column10);

		objectOutput.writeInt(column11);

		objectOutput.writeInt(column12);

		objectOutput.writeInt(column13);

		objectOutput.writeInt(column14);

		objectOutput.writeInt(column15);

		objectOutput.writeInt(column16);

		objectOutput.writeInt(column17);

		objectOutput.writeInt(column18);

		objectOutput.writeInt(column19);

		objectOutput.writeInt(column20);

		objectOutput.writeInt(column21);

		objectOutput.writeInt(column22);

		objectOutput.writeInt(column23);

		objectOutput.writeInt(column24);

		objectOutput.writeInt(column25);

		objectOutput.writeInt(column26);

		objectOutput.writeInt(column27);

		objectOutput.writeInt(column28);

		objectOutput.writeInt(column29);

		objectOutput.writeInt(column30);

		objectOutput.writeInt(column31);

		objectOutput.writeInt(column32);

		objectOutput.writeInt(column33);

		objectOutput.writeInt(column34);

		objectOutput.writeInt(column35);

		objectOutput.writeInt(column36);

		objectOutput.writeInt(column37);

		objectOutput.writeInt(column38);

		objectOutput.writeInt(column39);

		objectOutput.writeInt(column40);

		objectOutput.writeInt(column41);

		objectOutput.writeInt(column42);

		objectOutput.writeInt(column43);

		objectOutput.writeInt(column44);

		objectOutput.writeInt(column45);

		objectOutput.writeInt(column46);

		objectOutput.writeInt(column47);

		objectOutput.writeInt(column48);

		objectOutput.writeInt(column49);

		objectOutput.writeInt(column50);

		objectOutput.writeInt(column51);

		objectOutput.writeInt(column52);

		objectOutput.writeInt(column53);

		objectOutput.writeInt(column54);

		objectOutput.writeInt(column55);

		objectOutput.writeInt(column56);

		objectOutput.writeInt(column57);

		objectOutput.writeInt(column58);

		objectOutput.writeInt(column59);

		objectOutput.writeInt(column60);

		objectOutput.writeInt(column61);

		objectOutput.writeInt(column62);

		objectOutput.writeInt(column63);

		objectOutput.writeInt(column64);
	}

	public long manyColumnsEntryId;
	public int column1;
	public int column2;
	public int column3;
	public int column4;
	public int column5;
	public int column6;
	public int column7;
	public int column8;
	public int column9;
	public int column10;
	public int column11;
	public int column12;
	public int column13;
	public int column14;
	public int column15;
	public int column16;
	public int column17;
	public int column18;
	public int column19;
	public int column20;
	public int column21;
	public int column22;
	public int column23;
	public int column24;
	public int column25;
	public int column26;
	public int column27;
	public int column28;
	public int column29;
	public int column30;
	public int column31;
	public int column32;
	public int column33;
	public int column34;
	public int column35;
	public int column36;
	public int column37;
	public int column38;
	public int column39;
	public int column40;
	public int column41;
	public int column42;
	public int column43;
	public int column44;
	public int column45;
	public int column46;
	public int column47;
	public int column48;
	public int column49;
	public int column50;
	public int column51;
	public int column52;
	public int column53;
	public int column54;
	public int column55;
	public int column56;
	public int column57;
	public int column58;
	public int column59;
	public int column60;
	public int column61;
	public int column62;
	public int column63;
	public int column64;

}