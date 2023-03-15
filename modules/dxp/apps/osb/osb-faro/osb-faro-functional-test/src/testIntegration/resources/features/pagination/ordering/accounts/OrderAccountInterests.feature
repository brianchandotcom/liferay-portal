@spira_Order @Order @Accounts @List @team_FARO @priority_3
Feature: Order an Account Profile's Interests
	As an Business User, I should be able to order an Account's Interests

	Background: [Setup] Navigate to an Account
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Accounts" page
		* I click "Hilll, Gottlieb and Dicki" in the table

	Scenario: Order an Account Profile's Interests
		Given I click the "Interests" tab

		# Order by Topic

		When I sort the table by the "Topic" column header in descending order
		Then I should see the following ordered rows in the bar graph table:
			| web-enabled interfaces   |
			| visionary platforms      |
			| synergistic schemas      |
			| rich e-commerce          |
			| mesh synergistic schemas |
			| mesh                     |
			| cutting-edge platforms   |
		When I sort the table by the "Topic" column header in ascending order
		Then I should see the following ordered rows in the bar graph table:
			| cutting-edge platforms   |
			| mesh                     |
			| mesh synergistic schemas |
			| rich e-commerce          |
			| synergistic schemas      |
			| visionary platforms      |
			| web-enabled interfaces   |

		# Order by Account Members

		When I sort the table by the "Account Members" column header in descending order
		Then I should see the following ordered rows in the bar graph table:
			| cutting-edge platforms   |
			| visionary platforms      |
			| web-enabled interfaces   |
			| mesh                     |
			| mesh synergistic schemas |
			| rich e-commerce          |
			| synergistic schemas      |
		When I sort the table by the "Account Members" column header in ascending order
		Then I should see the following ordered rows in the bar graph table:
			| mesh                     |
			| mesh synergistic schemas |
			| rich e-commerce          |
			| synergistic schemas	   |
			| web-enabled interfaces   |
			| cutting-edge platforms   |
			| visionary platforms      |
