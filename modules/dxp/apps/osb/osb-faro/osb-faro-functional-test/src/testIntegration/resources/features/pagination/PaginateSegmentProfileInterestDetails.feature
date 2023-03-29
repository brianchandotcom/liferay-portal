@spira_Pagination @Pagination @Segments @List @Profile @team_FARO @priority_3
Feature: Paginate Segment Profile in an Interest detail
	As a Business User, I should be able to paginate to the second page in Interest detail

	Background: [Setup] Navigate to Segment
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Segments" page
		* I click "everybody" in the table

	Scenario: Paginate to second page in Interest Details
		Given I click the "Interests" tab
		And I click "rich e-commerce" in the table
		When I go to page "2" in the table
		Then I should be on page "2" in the table
		And I should see an individual named "Monica Johnson" in the table