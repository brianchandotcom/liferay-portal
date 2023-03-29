@spira_Search @Search @Data_Source @Deletion @List @team_FARO @priority_3 @blocked
Feature: Search Unaffected Individuals List During Data Source Deletion
	As an Business User, I should not be able to search for unaffected Individuals during Data Source deletion

	Background:[Setup] Add a CSV Data Source with an Individuals
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Data Source" page
		* I click the "Add Data Source" button
		* I create a file named "SearchUnaffectedIndividuals A - ${Random.1}.csv" with the following content:
			"""
			email,givenName,familyName,jobTitle
			A${Random.2}@gmail.com,firstA${Random.2},lastA${Random.2},Farmer
			"""
		* I click the "CSV File" button
		* I browse for a file named "SearchUnaffectedIndividuals A - ${Random.1}.csv"
		* I name the Data Source "SearchUnaffectedIndividuals A - ${Random.1}.csv"
		* I click the "Next" button
		* I should see "4" fields mapped and "0" fields not mapped
		* I click the "Done" button
		* I should see that "SearchUnaffectedIndividuals A - ${Random.1}.csv" was successfully uploaded
		* I go to the "Data Source" page
		* I click the "Add Data Source" button
		* I create a file named "SearchUnaffectedIndividuals B - ${Random.1}.csv" with the following content:
			"""
			email,givenName,familyName,jobTitle
			B${Random.2}@gmail.com,firstB${Random.2},lastB${Random.2},Teacher
			"""
		* I click the "CSV File" button
		* I browse for a file named "SearchUnaffectedIndividuals B - ${Random.1}.csv"
		* I name the Data Source "SearchUnaffectedIndividuals B - ${Random.1}.csv"
		* I click the "Next" button
		* I should see "4" fields mapped and "0" fields not mapped
		* I click the "Done" button
		* I should see that "SearchUnaffectedIndividuals B - ${Random.1}.csv" was successfully uploaded
		* I go to the "Individuals" page
		* I click the "Known Individuals" tab
		* I search for "firstA${Random.2} lastA${Random.2}"
		* I should see an item named "firstA${Random.2} lastA${Random.2}" in the table
		* I search for "firstB${Random.2} lastB${Random.2}"
		* I should see an item named "firstB${Random.2} lastB${Random.2}" in the table

	Scenario: Assert Unaffected Individual's Aren't Returned During Data Source Deletion
		Given I go to the "Data Source" page
		And I click "SearchUnaffectedIndividuals A - ${Random.1}.csv" in the table
		When I click the "Delete Data Source" button
		And I click the affected "Individuals" on the Data Source deletion page
		And I search for "firstB${Random.2} lastB${Random.2}"
		Then I should not see an individual named "firstB${Random.2} lastB${Random.2}" in the table
		When I search for "firstA${Random.2} lastA${Random.2}"
		Then I should see an individual named "firstA${Random.2} lastA${Random.2}" in the table