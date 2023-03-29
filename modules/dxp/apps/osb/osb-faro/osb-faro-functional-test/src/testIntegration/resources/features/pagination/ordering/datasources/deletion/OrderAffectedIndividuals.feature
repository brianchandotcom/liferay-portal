@spira_Order @Order @Data_Source @Deletion @team_FARO @priority_3 @blocked
Feature: Order Affected Individuals During Data Source Deletion
	As an Business User, I should be able to order Individuals affected by Data Source deletion by name

	Background:[Setup] Add a CSV Data Source with an Individuals
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Data Source" page
		* I click the "Add Data Source" button
		* I create a file named "OrderAffectedIndividuals - ${Random.1}.csv" with the following content:
			"""
			email,givenName,familyName,jobTitle
			A${Random.2}@gmail.com,Afirst${Random.2},Alast${Random.2},Farmer
			B${Random.2}@gmail.com,Bfirst${Random.2},Blast${Random.2},Farmer
			C${Random.2}@gmail.com,Cfirst${Random.2},Clast${Random.2},Farmer
			D${Random.2}@gmail.com,Dfirst${Random.2},Dlast${Random.2},Farmer
			"""
		* I click the "CSV File" button
		* I browse for a file named "OrderAffectedIndividuals - ${Random.1}.csv"
		* I name the Data Source "OrderAffectedIndividuals - ${Random.1}.csv"
		* I click the "Next" button
		* I should see "4" fields mapped and "0" fields not mapped
		* I click the "Done" button
		* I should see that "OrderAffectedIndividuals - ${Random.1}.csv" was successfully uploaded
		
	Scenario: Order Affected Individuals by Name
		Given I go to the "Data Source" page
		And I click "OrderAffectedIndividuals - ${Random.1}.csv" in the table
		And I click the "Delete Data Source" button
		And I should see that "4 Individuals" will be affected on the Data Source deletion page
		And I click the affected "Individuals" on the Data Source deletion page
		When I should see the users sorted in ascending alphabetical order
		And I sort the table by the "Name | Email" column header in descending order
		Then I should see the users sorted in descending alphabetical order