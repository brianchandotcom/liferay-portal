# Sass Compiler JNI

This module provides a LibSass wrapper, which allows you to use the native
library locally. The native Sass library requires a specific environment in
order to build. Once on the correct environment, you can run the related Gradle
build task for creating the LibSass library. The following instructions are for
building native libraries for the compiler to use.

## Building the Darwin LibSass Library

The following instructions builds the `resources/darwin/libsass.dylib` library.

1. Install OSX 10.7.5.
2. Login to [developer.apple.com](developer.apple.com).
3. Download *Command Line Tools (OS X Mountain Lion) for Xcode - April 2013*.
4. Install package `xcode462_cltools_10_76938260a.dmg`.
5. Execute `gradle buildLibSass_Darwin` from `sass-compiler`.

## Building the Linux 32-bit LibSass Library

The following instructions builds the `resources/linux-x86/libsass.so` library.

1. Install Ubuntu 10.04 LTS 32-bit from
   [http://old-releases.ubuntu.com/releases/10.04.3/ubuntu-10.04.4-desktop-i386.iso](http://old-releases.ubuntu.com/releases/10.04.3/ubuntu-10.04.4-desktop-i386.iso).
2. Install `gcc 4.6`.
3. Install `g++ 4.6`.
4. Execute `gradle buildLibSass_Linux_x86` from `sass-compiler`.

## Building the Linux 64-bit LibSass Library

The following instructions builds the `resources/linux-x86-64/libsass.so`
library.

1. Install Ubuntu 10.04 LTS 64-bit from
   [http://old-releases.ubuntu.com/releases/10.04.3/ubuntu-10.04.4-desktop-amd64.iso](http://old-releases.ubuntu.com/releases/10.04.3/ubuntu-10.04.4-desktop-amd64.iso).
2. Install `gcc 4.6`.
3. Install `g++ 4.6`.
4. Execute `gradle buildLibSass_Linux_x86_64` from `sass-compiler`.

## Building the Windows 32-bit LibSass Library

The following instructions builds the `resources/win32-x86/sass.dll` library.

1. Install Windows 7 32-bit.
2. Download `tdm-gcc` 32-bit from
   [http://sourceforge.net/projects/tdm-gcc/files/TDM-GCC%20Installer/tdm-gcc-5.1.0-3.exe/download](http://sourceforge.net/projects/tdm-gcc/files/TDM-GCC%20Installer/tdm-gcc-5.1.0-3.exe/download).
3. Run the `tdm-gcc` 32-bit installer and then click *Create*.
4. Install with all the defaults.
5. Go to the installation folder `\TDM-GCC-32\bin`.
6. Execute `gradle buildLibSass_Win32_x86` from `sass-compiler`.

## Building the Windows 64-bit LibSass Library

The following instructions builds the `resources/win32-x86-64/sass.dll` library.

1. Install Windows 7 64-bit.
2. Download `tdm64-gcc` 64-bit from
   [http://sourceforge.net/projects/tdm-gcc/files/TDM-GCC%20Installer/tdm64-gcc-5.1.0-2.exe/download](http://sourceforge.net/projects/tdm-gcc/files/TDM-GCC%20Installer/tdm64-gcc-5.1.0-2.exe/download).
3. Run the `tdm64-gcc` 64-bit installer and then click *Create*.
4. Select *MinGW-w64/TDM64* option.
5. Install with all the defaults.
6. Go to the installation folder `\TDM64-GCC-64\bin`.
7. Execute `gradle buildLibSass_Win32_x86_64` from `sass-compiler`.