@spira_Data_Source @Data_Source @CSV @team_FARO @priority_5 @blocked
Feature: Edit CSV Data Source
	As a Business User, I should be able to upload and edit a CSV Data Source

	Background: [Setup] Upload a CSV Data Source and reach the Data Transformation page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Data Source" page
		* I click the "Add Data Source" button
		* I create a file named "EditCSVDataSource - ${Random.1}.csv" with the following content:
			"""
			email,givenName,familyName,jobTitle
			Opal.Henderson@gmail.com,Opal,Henderson,Economist
			Nettie.Morgan@gmail.com,Nettie,Morgan,Civil Engineer
			"""
		* I click the "CSV File" button
		* I browse for a file named "EditCSVDataSource - ${Random.1}.csv"
		* I name the Data Source "EditCSVDataSource - ${Random.1}.csv"
		* I click the "Next" button
		* I should see "4" fields mapped and "0" fields not mapped
		* I click the "Done" button
		* I should see that "EditCSVDataSource - ${Random.1}.csv" was successfully uploaded
		* I go to the "Data Source" page
		* I search for "EditCSVDataSource - ${Random.1}.csv"
		* I click "EditCSVDataSource - ${Random.1}.csv" in the table

	Scenario: Edit CSV Data Source - Name
		When I edit the Data Source name to "EditCSVDataSource - ${Random.1} EDITED"
		Then I should see that "EditCSVDataSource - ${Random.1} EDITED" was successfully edited

	Scenario: Edit CSV Data Source - Delete a Field
		Given I click the "Edit CSV" button
		And I should see "4" fields mapped and "0" fields not mapped
		And I delete the data model match in row "1"
		Then I should see "3" fields mapped and "0" fields not mapped
		When I click the "Done" button
		Then I should see "3" rows of data model matches