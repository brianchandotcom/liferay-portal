package fileutils

import (
	"archive/zip"
	"fmt"
	"io"
	"io/ioutil"
	"log"
	"os"
	"path/filepath"

	"installer/constants"

	"github.com/otiai10/copy"
)

// CopyTask represents a copy task. Once created, can be called with Execute.
type CopyTask struct {
	From, To string
}

// Execute executes the copy job
func (copyTask CopyTask) Execute() error {
	if copyTask.From == "" || copyTask.To == "" {
		return fmt.Errorf(
			"From: %q or To: %q are empty", copyTask.From, copyTask.To)
	}

	if !Exists(copyTask.From) {
		return fmt.Errorf(
			"From: %q path does not exist", copyTask.From, copyTask.To)
	}

	err := copy.Copy(copyTask.From, copyTask.To)
	if err != nil {
		return fmt.Errorf(
			"Copy operation failed: %s -> %s: %v",
			copyTask.From, copyTask.To, err)
	}

	return nil
}

func Exists(path string) bool {
	if _, err := os.Stat(path); os.IsNotExist(err) {
		return false
	}

	return true
}

func GetSrcComponentDefinitionName() string {
	files, err := ioutil.ReadDir(constants.ComponentDefinitionDir)
	if err != nil || len(files) == 0 {
		log.Fatalf("Unable to read the component definition file: %v", err)
	}

	return files[0].Name()
}

// Get the definition file path from the installer's dependencies
func GetSrcComponentDefinitionPath() string {
	return filepath.Join(constants.ComponentDefinitionDir,
		GetSrcComponentDefinitionName())
}

func GetCurrentWorkingDirectory() string {
	dir, err := os.Getwd()
	if err != nil {
		log.Fatal(err)
	}

	return dir
}

func Unzip(src, dest string) error {
	r, err := zip.OpenReader(src)
	if err != nil {
		return err
	}
	defer func() {
		if err := r.Close(); err != nil {
			panic(err)
		}
	}()

	os.MkdirAll(dest, 0755)

	extractAndWriteFile := func(f *zip.File) error {
		rc, err := f.Open()
		if err != nil {
			return err
		}
		defer func() {
			if err := rc.Close(); err != nil {
				panic(err)
			}
		}()

		path := filepath.Join(dest, f.Name)

		if f.FileInfo().IsDir() {
			os.MkdirAll(path, f.Mode())
		} else {
			os.MkdirAll(filepath.Dir(path), f.Mode())
			f, err := os.OpenFile(
				path, os.O_WRONLY|os.O_CREATE|os.O_TRUNC, f.Mode())
			if err != nil {
				return err
			}
			defer func() {
				if err := f.Close(); err != nil {
					panic(err)
				}
			}()

			_, err = io.Copy(f, rc)
			if err != nil {
				return err
			}
		}
		return nil
	}

	for _, f := range r.File {
		err := extractAndWriteFile(f)
		if err != nil {
			return err
		}
	}

	return nil
}
