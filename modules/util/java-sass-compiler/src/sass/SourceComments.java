package sass;

public enum SourceComments {
	NONE((byte)0), DEFAULT((byte)1), MAP((byte)2);


	byte value;
	private SourceComments(byte value) {
		this.value=value;
	}
	public byte value() {
		return value;
	}
}
