@spira_Data_Source @Data_Source @CSV @team_FARO @priority_5 @blocked
Feature: Add a CSV Data Source - Happy Path
	As a Business User, I should be able to upload a CSV as a Data Source

	Background: [Setup] Navigate to the Data Source Page and Click to Add a Data Source
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Data Source" page
		* I click the "Add Data Source" button

	Scenario: Add a CSV Data Source with Default Values
		Given I create a file named "AddCSVDataSource - ${Random.1}.csv" with the following content:
			"""
			email,givenName,familyName,jobTitle
			${Random.2}@gmail.com,first${Random.2},last${Random.2},Economist
			${Random.3}@gmail.com,first${Random.3},last${Random.3},Civil Engineer
			"""
		And I click the "CSV File" button
		When I browse for a file named "AddCSVDataSource - ${Random.1}.csv"
		When I name the Data Source "AddCSVDataSource - ${Random.1}.csv"
		And I click the "Next" button
		And I should see "4" fields mapped and "0" fields not mapped
		And I click the "Done" button
		Then I should see that "AddCSVDataSource - ${Random.1}.csv" was successfully uploaded
		When I go to the "Data Source" page
		And I search for "AddCSVDataSource - ${Random.1}.csv"
		Then I should see a "CSV" Data Source named "AddCSVDataSource - ${Random.1}.csv"
		When I go to the "Individuals" page
		And I click the "Known Individuals" tab
		And I search for "first${Random.2} last${Random.2}"
		Then I should see an item named "first${Random.2} last${Random.2}" in the table
		And I should only see Individuals named "first${Random.2} last${Random.2}" in the table